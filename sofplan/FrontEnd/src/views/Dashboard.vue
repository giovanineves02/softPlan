<template>
  <div class="min-h-screen bg-[#030D24] text-white [background-image:radial-gradient(circle_at_18%_0%,rgba(37,99,235,0.18),transparent_34%),radial-gradient(circle_at_90%_30%,rgba(34,211,238,0.07),transparent_28%)]">
    <header class="border-b border-[#172B5A] bg-[#061536]">
      <div class="mx-auto flex h-[72px] max-w-[1280px] items-center justify-between px-5 lg:px-8">
        <div class="flex items-center gap-3">
          <img :src="logo" alt="Logo SOFTPLAN" class="h-10 w-10 rounded-lg object-cover" />
          <div class="flex items-center text-lg font-bold tracking-tight">
            <span>SOFT</span><span class="text-[#22D3EE]">PLAN</span>
          </div>
        </div>

        <div class="relative flex items-center gap-3">
          <span class="hidden text-[10px] font-medium uppercase tracking-[0.22em] text-[#22D3EE] sm:block">Dashboard</span>
          <button type="button" aria-label="Abrir menu principal" :aria-expanded="menuAberto" class="flex h-10 w-10 cursor-pointer items-center justify-center rounded-lg border border-[#2250A5] bg-[#0B204A] text-[#93B8FF] transition-all duration-200 hover:border-[#22D3EE] hover:text-[#22D3EE]" @click="menuAberto = !menuAberto">
            <svg v-if="!menuAberto" aria-hidden="true" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2"><path stroke-linecap="round" d="M4 6h16M4 12h16M4 18h16" /></svg>
            <svg v-else aria-hidden="true" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2"><path stroke-linecap="round" d="M6 6l12 12M18 6L6 18" /></svg>
          </button>

          <nav v-if="menuAberto" aria-label="Menu principal" class="absolute right-0 top-12 z-30 w-56 rounded-xl border border-[#1C3D7A] bg-[#081B42] p-2 shadow-[0_16px_40px_rgba(0,0,0,0.35)]">
            <p class="px-3 pb-2 pt-1 text-[10px] font-semibold uppercase tracking-[0.2em] text-[#5B80B8]">Navegação</p>
            <button v-for="item in menuItems" :key="item.label" type="button" :disabled="item.comingSoon" class="flex w-full items-center gap-3 rounded-lg px-3 py-2.5 text-left text-sm transition-colors duration-200 disabled:cursor-not-allowed" :class="item.active ? 'bg-[#12336F] text-[#22D3EE]' : 'text-[#93B8FF] hover:bg-[#0C2858] hover:text-white disabled:opacity-50 disabled:hover:bg-transparent disabled:hover:text-[#93B8FF]'" @click="selecionarMenu(item)">
              <span class="h-1.5 w-1.5 rounded-full" :class="item.active ? 'bg-[#22D3EE]' : 'bg-[#315B9E]'" />
              {{ item.label }}
              <span v-if="item.comingSoon" class="ml-auto text-[9px] uppercase tracking-wider text-[#5B80B8]">Em breve</span>
            </button>
          </nav>
        </div>
      </div>
    </header>

    <main class="mx-auto max-w-[1152px] px-5 pb-12 pt-9 lg:px-8 lg:pt-11">
      <div class="mb-6">
        <p class="mb-2 text-[8px] font-semibold uppercase tracking-[0.28em] text-[#22D3EE]">Análise de dados</p>
        <div class="flex flex-wrap items-end justify-between gap-3">
          <div>
            <h1 class="text-2xl font-bold tracking-tight text-[#EAF1FF]">Dashboard</h1>
            <p class="mt-1 text-[11px] text-[#6E8DCE]">Visão consolidada dos dados importados — clientes, faturamento e serviços.</p>
          </div>
          <span v-if="store.arquivo" class="max-w-[210px] truncate text-[9px] text-[#5B80B8]" :title="store.arquivo.name">{{ store.arquivo.name }}</span>
        </div>
      </div>

      <section v-if="!store.dadosTratados.length" class="rounded-xl border border-[#142D5C] bg-[#071632] p-8 text-center">
        <div class="mx-auto mb-3 flex h-10 w-10 items-center justify-center rounded-lg border border-[#16427C] bg-[#0C234B] text-[#22D3EE]">
          <svg class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="1.6"><path stroke-linecap="round" stroke-linejoin="round" d="M4 19V5m0 14h16M8 15v-4m4 4V7m4 8V9" /></svg>
        </div>
        <h2 class="text-sm font-semibold text-white">Seu dashboard aparecerá aqui</h2>
        <p class="mx-auto mt-2 max-w-md text-xs leading-5 text-[#6E8DCE]">Envie uma planilha para calcular indicadores e distribuições automaticamente com os dados importados.</p>
        <button type="button" class="mt-5 rounded-md bg-[#2563EB] px-4 py-2 text-xs font-semibold text-white transition hover:bg-[#1D4ED8]" @click="router.push('/upload')">Enviar planilha</button>
      </section>

      <template v-else>
        <section class="grid gap-3 sm:grid-cols-3">
          <article v-for="card in cards" :key="card.titulo" class="min-h-[82px] rounded-[10px] border border-[#142D5C] bg-[#071632] px-4 py-4">
            <p class="text-[7px] font-semibold uppercase tracking-[0.24em] text-[#5B80B8]">{{ card.titulo }}</p>
            <p class="mt-2 text-[27px] font-bold leading-none tracking-tight" :class="card.cor">{{ card.valor }}</p>
            <p v-if="card.nota" class="mt-1 text-[8px] text-[#5B80B8]">{{ card.nota }}</p>
          </article>
        </section>

        <section class="mt-4 grid gap-3 sm:grid-cols-2">
          <article class="min-h-[259px] rounded-[10px] border border-[#142D5C] bg-[#071632] px-5 py-5">
            <h2 class="text-[10px] font-semibold text-[#EAF1FF]">Distribuição por Segmento</h2>
            <template v-if="store.dadosDashboard.segmentos.length">
              <div class="mt-4 flex justify-center">
                <svg viewBox="0 0 140 140" class="h-[145px] w-[145px] -rotate-90" role="img" aria-label="Gráfico de rosca da distribuição por segmento">
                  <circle cx="70" cy="70" r="52" fill="none" stroke="#102650" stroke-width="23" />
                  <circle v-for="(segmento, indice) in segmentosGrafico" :key="segmento.label" cx="70" cy="70" r="52" fill="none" :stroke="cores[indice % cores.length]" stroke-width="23" :stroke-dasharray="`${segmento.comprimento} ${circunferencia}`" :stroke-dashoffset="-segmento.inicio" />
                </svg>
              </div>
              <div class="mt-1 flex flex-wrap justify-center gap-x-2.5 gap-y-1">
                <span v-for="(segmento, indice) in segmentosGrafico" :key="segmento.label" class="flex items-center gap-1 text-[8px] text-[#8AA6D4]"><i class="h-1.5 w-1.5 rounded-full" :style="{ backgroundColor: cores[indice % cores.length] }" />{{ segmento.label }} <span class="text-[#5B80B8]">{{ segmento.quantidade }}</span></span>
              </div>
            </template>
            <div v-else class="flex h-[205px] items-center justify-center text-center text-[10px] text-[#5B80B8]">Não há uma coluna de segmento nesta planilha.</div>
          </article>

          <article class="min-h-[259px] rounded-[10px] border border-[#142D5C] bg-[#071632] px-5 py-5">
            <h2 class="text-[10px] font-semibold text-[#EAF1FF]">Clientes por Nível (A/B/C)</h2>
            <template v-if="niveisGrafico.length">
              <div class="mt-5 flex h-[176px]">
                <div class="flex w-7 flex-col justify-between pb-5 text-right text-[7px] text-[#5074AC]"><span>{{ maxNivel }}</span><span>{{ Math.round(maxNivel * 0.75) }}</span><span>{{ Math.round(maxNivel * 0.5) }}</span><span>{{ Math.round(maxNivel * 0.25) }}</span><span>0</span></div>
                <div class="relative ml-2 flex flex-1 items-end justify-around border-b border-[#142D5C] pb-0">
                  <div class="pointer-events-none absolute inset-x-0 top-0 flex h-[calc(100%-20px)] flex-col justify-between"><i v-for="n in 4" :key="n" class="border-t border-dashed border-[#102650]" /></div>
                  <div v-for="(nivel, indice) in niveisGrafico" :key="nivel.label" class="relative z-[1] flex h-full w-1/4 flex-col items-center justify-end">
                    <span class="mb-1 text-[8px] text-[#8AA6D4]">{{ nivel.quantidade }}</span>
                    <div class="w-6 rounded-t-[4px] sm:w-8" :style="{ height: `${Math.max((nivel.quantidade / maxNivel) * 130, nivel.quantidade ? 4 : 0)}px`, backgroundColor: cores[(indice + 1) % cores.length] }" />
                    <span class="mt-1.5 h-4 text-[7px] text-[#5B80B8]">{{ nivel.label }}</span>
                  </div>
                </div>
              </div>
            </template>
            <div v-else class="flex h-[205px] items-center justify-center text-center text-[10px] text-[#5B80B8]">Não há uma coluna de nível de cliente nesta planilha.</div>
          </article>
        </section>

        <section class="mt-4 grid gap-3 sm:grid-cols-2">
          <article class="rounded-[10px] border border-[#142D5C] bg-[#071632] p-5">
            <div class="flex items-center justify-between gap-3"><div><h2 class="text-[10px] font-semibold text-[#EAF1FF]">Qualidade dos dados</h2><p class="mt-1 text-[9px] text-[#5B80B8]">Resultado das validações da importação</p></div><button type="button" class="text-[9px] font-medium text-[#22D3EE] hover:text-white" @click="router.push('/relatorio')">Ver detalhes →</button></div>
            <div class="mt-4 flex items-end justify-between"><div><p class="text-2xl font-bold text-emerald-300">{{ store.linhasValidas }}</p><p class="text-[8px] text-[#5B80B8]">registros válidos</p></div><div class="text-right"><p class="text-2xl font-bold text-rose-300">{{ store.linhasComErro }}</p><p class="text-[8px] text-[#5B80B8]">com ocorrências</p></div></div>
            <div class="mt-3 h-1.5 overflow-hidden rounded-full bg-[#142D5C]"><div class="h-full rounded-full bg-gradient-to-r from-[#22D3EE] to-[#4D84F5]" :style="{ width: `${store.totalLinhas ? (store.linhasValidas / store.totalLinhas) * 100 : 0}%` }" /></div>
          </article>
          <article class="rounded-[10px] border border-[#142D5C] bg-[#071632] p-5">
            <h2 class="text-[10px] font-semibold text-[#EAF1FF]">Resumo da importação</h2>
            <p class="mt-2 truncate text-[10px] text-[#8AA6D4]" :title="store.arquivo?.name">{{ store.arquivo?.name }}</p>
            <div class="mt-4 flex gap-8"><div><p class="text-lg font-bold text-[#22D3EE]">{{ store.totalLinhas.toLocaleString('pt-BR') }}</p><p class="text-[8px] text-[#5B80B8]">registros</p></div><div><p class="text-lg font-bold text-[#7AA8FF]">{{ store.totalColunas }}</p><p class="text-[8px] text-[#5B80B8]">colunas</p></div><div><p class="text-lg font-bold text-white">{{ store.erros.length }}</p><p class="text-[8px] text-[#5B80B8]">ocorrências</p></div></div>
          </article>
        </section>
      </template>
    </main>
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'
import logo from '../assets/logo.png'
import { useuploadStore } from '../store/uploadStore'

const router = useRouter()
const store = useuploadStore()
const menuAberto = ref(false)
const menuItems = [
  { label: 'Dashboard', active: true },
  { label: 'Importar dados', active: false },
  { label: 'Relatório de validação', active: false },
  { label: 'Histórico', active: false, comingSoon: true },
  { label: 'Configurações', active: false, comingSoon: true }
]
const cores = ['#22D3EE', '#4D84F5', '#7AA8FF', '#9A7BFF', '#36C994', '#F4B544']
const circunferencia = 2 * Math.PI * 52
const segmentosGrafico = computed(() => {
  let acumulado = 0
  return store.dadosDashboard.segmentos.map((item) => {
    const comprimento = (item.quantidade / store.totalLinhas) * circunferencia
    const segmento = { ...item, inicio: acumulado, comprimento }
    acumulado += comprimento
    return segmento
  })
})
const niveisGrafico = computed(() => store.dadosDashboard.niveis.slice(0, 5))
const maxNivel = computed(() => Math.max(1, ...niveisGrafico.value.map((item) => item.quantidade)))
const formatarMoeda = (valor) => {
  if (valor === null) return '—'
  if (Math.abs(valor) >= 1000000) return `R$ ${(valor / 1000000).toLocaleString('pt-BR', { maximumFractionDigits: 1 })} mi`
  if (Math.abs(valor) >= 1000) return `R$ ${(valor / 1000).toLocaleString('pt-BR', { maximumFractionDigits: 1 })}k`
  return valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL', maximumFractionDigits: 0 })
}
const cards = computed(() => [
  { titulo: 'Total de clientes', valor: store.dadosDashboard.clientes.toLocaleString('pt-BR'), nota: store.dadosDashboard.camposDisponiveis.clientes ? 'clientes identificados' : 'registros importados', cor: 'text-[#22D3EE]' },
  { titulo: 'Faturamento médio', valor: formatarMoeda(store.dadosDashboard.faturamentoMedio), nota: store.dadosDashboard.camposDisponiveis.faturamento ? 'média dos valores encontrados' : 'coluna de valor não identificada', cor: 'text-[#4D84F5]' },
  { titulo: 'Serviços ativos', valor: store.dadosDashboard.servicosAtivos?.toLocaleString('pt-BR') ?? '—', nota: store.dadosDashboard.camposDisponiveis.servicos ? 'serviços identificados' : 'coluna de serviço não identificada', cor: 'text-[#7AA8FF]' }
])

function selecionarMenu(item) {
  if (item.label === 'Dashboard') router.push('/dashboard')
  if (item.label === 'Importar dados') router.push('/upload')
  if (item.label === 'Relatório de validação') router.push('/relatorio')
  menuAberto.value = false
}
</script>
