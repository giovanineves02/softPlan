import { defineStore } from 'pinia'
import * as XLSX from 'xlsx'

const normalizarCabecalho = (texto) => String(texto).normalize('NFD').replace(/[\u0300-\u036f]/g, '').toLowerCase()
const normalizarChave = (texto) => normalizarCabecalho(texto).replace(/[^a-z0-9]/g, '')
const converterNumero = (valor) => {
  if (typeof valor === 'number') return valor
  const texto = String(valor ?? '').trim().replace(/[^\d,.-]/g, '')
  if (!texto) return Number.NaN
  const normalizado = texto.includes(',')
    ? texto.replace(/\./g, '').replace(',', '.')
    : texto
  return Number(normalizado)
}

const encontrarColuna = (colunas, aliases) => colunas.find((coluna) => {
  const chave = normalizarChave(coluna)
  return aliases.some((alias) => chave.includes(alias))
})

const contarValores = (linhas, coluna) => {
  if (!coluna) return []
  const totais = new Map()
  linhas.forEach((linha) => {
    const valor = String(linha[coluna] ?? '').trim()
    if (valor) totais.set(valor, (totais.get(valor) || 0) + 1)
  })
  return [...totais.entries()].map(([label, quantidade]) => ({ label, quantidade })).sort((a, b) => b.quantidade - a.quantidade)
}

export const useuploadStore = defineStore('upload', {
  // STATE: arquivo, dados tratados e ocorrências encontradas na análise.
  state: () => ({
    arquivo: null,
    dadosOriginais: [],
    dadosTratados: [],
    erros: [],
    erro: ''
  }),

  // GETTERS: totais e agrupamentos calculados para o relatório.
  getters: {
    totalLinhas: (state) => state.dadosTratados.length,
    totalColunas: (state) => state.dadosTratados.length ? Object.keys(state.dadosTratados[0]).length : 0,
    colunas: (state) => state.dadosTratados.length ? Object.keys(state.dadosTratados[0]) : [],
    linhasComErro: (state) => new Set(state.erros.map((item) => item.linha)).size,
    linhasValidas() {
      return this.totalLinhas - this.linhasComErro
    },
    errosPorTipo: (state) => state.erros.reduce((totais, item) => {
      totais[item.tipo] = (totais[item.tipo] || 0) + 1
      return totais
    }, {}),
    dadosDashboard: (state) => {
      const linhas = state.dadosTratados
      const colunas = linhas.length ? Object.keys(linhas[0]) : []
      const colunaCliente = encontrarColuna(colunas, ['cliente', 'customer', 'nome'])
      const colunaFaturamento = encontrarColuna(colunas, ['faturamento', 'receita', 'revenue', 'valorcontrato', 'valor mensal', 'valor'])
      const colunaServico = encontrarColuna(colunas, ['servico', 'service'])
      const colunaSegmento = encontrarColuna(colunas, ['segmento', 'setor', 'industry'])
      const colunaNivel = encontrarColuna(colunas, ['nivelcliente', 'nivel', 'classecliente', 'classificacao'])
      const clientes = colunaCliente
        ? new Set(linhas.map((linha) => String(linha[colunaCliente] ?? '').trim()).filter(Boolean)).size
        : linhas.length
      const valoresFaturamento = colunaFaturamento
        ? linhas.map((linha) => converterNumero(linha[colunaFaturamento])).filter(Number.isFinite)
        : []

      return {
        clientes,
        faturamentoMedio: valoresFaturamento.length
          ? valoresFaturamento.reduce((soma, valor) => soma + valor, 0) / valoresFaturamento.length
          : null,
        servicosAtivos: colunaServico
          ? new Set(linhas.map((linha) => String(linha[colunaServico] ?? '').trim()).filter(Boolean)).size
          : null,
        segmentos: contarValores(linhas, colunaSegmento),
        niveis: contarValores(linhas, colunaNivel),
        camposDisponiveis: {
          clientes: Boolean(colunaCliente),
          faturamento: Boolean(colunaFaturamento),
          servicos: Boolean(colunaServico),
          segmentos: Boolean(colunaSegmento),
          niveis: Boolean(colunaNivel)
        }
      }
    }
  },

  // ACTIONS: leitura, tratamento, validação e limpeza.
  actions: {
    async lerArquivo(file) {
      this.erro = ''
      this.arquivo = file
      this.dadosOriginais = []
      this.dadosTratados = []
      this.erros = []

      if (!file) return

      const extensao = file.name.split('.').pop()?.toLowerCase()
      if (!['xlsx', 'xls', 'csv'].includes(extensao)) {
        this.erro = 'Formato inválido. Use XLSX, XLS ou CSV.'
        return
      }

      try {
        const buffer = await file.arrayBuffer()
        const workbook = XLSX.read(buffer, { type: 'array' })
        const worksheet = workbook.Sheets[workbook.SheetNames[0]]

        if (!worksheet) {
          this.erro = 'A planilha não contém uma aba para análise.'
          return
        }

        this.dadosOriginais = XLSX.utils.sheet_to_json(worksheet, { defval: '' })
        this.tratarDados()
      } catch (error) {
        console.error(error)
        this.erro = 'Não foi possível ler a planilha.'
      }
    },

    tratarDados() {
      const linhasVistas = new Map()
      const emailsVistos = new Map()
      const ocorrencias = []

      this.dadosTratados = this.dadosOriginais.map((linha, indice) => {
        const numeroLinha = indice + 2 // A primeira linha da planilha contém os cabeçalhos.
        const novaLinha = {}

        for (const [campo, valorOriginal] of Object.entries(linha)) {
          const cabecalho = normalizarCabecalho(campo)
          let valor = valorOriginal

          if (typeof valor === 'string') {
            const limpo = valor.trim().replace(/\s+/g, ' ')
            if (limpo !== valor) {
              ocorrencias.push({ linha: numeroLinha, campo, tipo: 'Espaços desnecessários', descricao: 'O valor continha espaços extras e foi ajustado.' })
            }
            valor = limpo
          }

          if (valor === '' || valor === null || valor === undefined) {
            ocorrencias.push({ linha: numeroLinha, campo, tipo: 'Campo obrigatório vazio', descricao: 'Preencha este campo para completar o registro.' })
            novaLinha[campo] = ''
            continue
          }

          if (cabecalho.includes('email') || cabecalho.includes('e-mail')) {
            const email = String(valor).trim().toLowerCase()
            if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
              ocorrencias.push({ linha: numeroLinha, campo, tipo: 'E-mail inválido', descricao: 'Informe um endereço de e-mail válido.' })
            }
            valor = email
            if (emailsVistos.has(email)) {
              ocorrencias.push({ linha: numeroLinha, campo, tipo: 'E-mail duplicado', descricao: `Este e-mail também aparece na linha ${emailsVistos.get(email)}.` })
            } else {
              emailsVistos.set(email, numeroLinha)
            }
          }

          if (cabecalho.includes('telefone') || cabecalho.includes('celular') || cabecalho.includes('phone') || cabecalho.includes('fone')) {
            const digitos = String(valor).replace(/\D/g, '')
            if (digitos.length < 10 || digitos.length > 13) {
              ocorrencias.push({ linha: numeroLinha, campo, tipo: 'Telefone fora do padrão', descricao: 'O telefone deve conter entre 10 e 13 dígitos.' })
            }
          }

          if ((cabecalho.includes('data') || cabecalho.includes('date')) && typeof valor === 'string' && Number.isNaN(Date.parse(valor))) {
            ocorrencias.push({ linha: numeroLinha, campo, tipo: 'Data fora do padrão', descricao: 'O valor não corresponde a uma data reconhecida.' })
          }

          if (typeof valor === 'string' && cabecalho === 'segmento') {
            const segmento = valor.toUpperCase()
            const segmentos = {
              'IND.': 'Indústria',
              INDUSTRIA: 'Indústria',
              'INDÚSTRIA': 'Indústria',
              COMERCIO: 'Comércio',
              'COMÉRCIO': 'Comércio',
              SERVICOS: 'Serviços',
              'SERVIÇOS': 'Serviços'
            }
            if (segmentos[segmento] && segmentos[segmento] !== valor) {
              ocorrencias.push({ linha: numeroLinha, campo, tipo: 'Texto padronizado', descricao: `O valor foi padronizado para “${segmentos[segmento]}”.` })
              valor = segmentos[segmento]
            }
          }

          if (typeof valor === 'string' && cabecalho === 'nivel_cliente' && valor !== valor.toUpperCase()) {
            valor = valor.toUpperCase()
            ocorrencias.push({ linha: numeroLinha, campo, tipo: 'Texto padronizado', descricao: 'O texto foi convertido para letras maiúsculas.' })
          }

          novaLinha[campo] = valor
        }

        const identidade = JSON.stringify(Object.keys(novaLinha).sort().map((campo) => String(novaLinha[campo]).toLowerCase()))
        if (linhasVistas.has(identidade)) {
          ocorrencias.push({ linha: numeroLinha, campo: 'Registro', tipo: 'Registro duplicado', descricao: `Este registro repete os dados da linha ${linhasVistas.get(identidade)}.` })
        } else {
          linhasVistas.set(identidade, numeroLinha)
        }

        return novaLinha
      })

      this.erros = ocorrencias
    },

    limpar() {
      this.arquivo = null
      this.dadosOriginais = []
      this.dadosTratados = []
      this.erros = []
      this.erro = ''
    }
  }
})