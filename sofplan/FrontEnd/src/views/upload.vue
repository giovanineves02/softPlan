<template>
  <div class="min-h-screen bg-[#030D24] text-white [background-image:radial-gradient(circle_at_18%_0%,rgba(37,99,235,0.18),transparent_34%),radial-gradient(circle_at_90%_30%,rgba(34,211,238,0.07),transparent_28%)]">
    <!--
      Header: você mencionou que vai criar um novo header para esta tela.
      Mantive o header atual (com o menu) intacto abaixo para não quebrar
      nada enquanto você não substitui — pode apagar este bloco inteiro
      quando o novo header estiver pronto.
    -->
    <header class="border-b border-[#172B5A] bg-[#061536]">
      <div class="mx-auto flex h-[72px] max-w-[1280px] items-center justify-between px-5 lg:px-8">
        <div class="flex items-center gap-3">
          <img :src="logo" alt="Logo SOFTPLAN" class="h-10 w-10 rounded-lg object-cover" />
          <div class="flex items-center text-lg font-bold tracking-tight">
            <span>SOFT</span><span class="text-[#22D3EE]">PLAN</span>
          </div>
        </div>

        <div class="relative flex items-center gap-3">
          <span class="hidden text-[10px] font-medium uppercase tracking-[0.22em] text-[#22D3EE] sm:block">Importação</span>
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
      <div class="mb-7">
        <p class="mb-3 text-[10px] font-semibold uppercase tracking-[0.28em] text-[#22D3EE]">Upload</p>
        <h1 class="text-3xl font-bold tracking-tight text-white md:text-4xl">Enviar planilha</h1>
        <p class="mt-3 max-w-2xl text-sm leading-6 text-[#6E8DCE]">
          Selecione XLSX, XLS ou CSV. O arquivo será lido no navegador e os dados serão processados automaticamente.
        </p>
      </div>

      <div class="grid gap-4 xl:grid-cols-[minmax(0,1fr)_260px]">
        <!-- Card: escolha do arquivo -->
        <section class="rounded-xl border border-[#1A3972] bg-[#081333]/90 p-6 shadow-[0_18px_50px_rgba(0,0,0,0.12)] sm:p-8">
          <h2 class="mb-5 text-base font-semibold text-white">Escolha o arquivo</h2>

          <!-- Botão estilo "input file" nativo + nome do arquivo -->
          <div class="mb-5 flex flex-wrap items-center gap-3">
            <label class="cursor-pointer rounded-md bg-[#2563EB] px-4 py-2.5 text-xs font-semibold text-white transition-colors duration-200 hover:bg-[#1D4ED8]">
              Escolher arquivo
              <input type="file" accept=".xlsx,.xls,.csv" class="sr-only" @change="escolherArquivo" />
            </label>
            <span class="text-xs text-[#6E8DCE]">
              {{ store.arquivo ? store.arquivo.name : 'Nenhum arquivo escolhido' }}
            </span>
          </div>

          <!-- Dropzone -->
          <label class="group flex min-h-[190px] cursor-pointer flex-col items-center justify-center rounded-lg border border-dashed border-[#2250A5] bg-[#061536]/80 px-5 text-center transition-colors duration-200 hover:border-[#22D3EE] hover:bg-[#0A1D45]" @dragover.prevent @drop.prevent="soltarArquivo">
            <input type="file" accept=".xlsx,.xls,.csv" class="sr-only" @change="escolherArquivo" />
            <span class="mb-3 flex h-11 w-11 items-center justify-center rounded-full border border-[#16427C] bg-[#0C234B] text-[#22D3EE] transition-transform duration-200 group-hover:-translate-y-1">
              <svg aria-hidden="true" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="1.7"><path stroke-linecap="round" stroke-linejoin="round" d="M12 16V4m0 0L8 8m4-4 4 4M4 14v4a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2v-4" /></svg>
            </span>
            <span class="text-sm font-medium text-[#93B8FF]">Ou arraste o arquivo aqui</span>
            <span class="mt-2 text-[10px] uppercase tracking-[0.18em] text-[#5B80B8]">.XLSX · .XLS · .CSV</span>
          </label>

          <p v-if="store.erro" class="mt-4 rounded-lg border border-red-400/30 bg-red-500/10 px-4 py-3 text-xs text-red-300">{{ store.erro }}</p>
        </section>

        <!-- Card: resumo -->
        <aside class="rounded-xl border border-[#1A3972] bg-[#081333]/90 p-6 shadow-[0_18px_50px_rgba(0,0,0,0.12)] sm:p-8">
          <h2 class="mb-6 text-base font-semibold text-white">Resumo</h2>
          <div class="space-y-5">
            <div>
              <p class="text-3xl font-bold text-[#22D3EE]">{{ store.totalLinhas }}</p>
              <p class="mt-1 text-xs text-[#5B80B8]">linhas carregadas</p>
            </div>
            <div class="h-px bg-[#172B5A]" />
            <div>
              <p class="text-3xl font-bold text-white">{{ store.totalColunas }}</p>
              <p class="mt-1 text-xs text-[#5B80B8]">colunas encontradas</p>
            </div>
          </div>
          <button v-if="store.dadosTratados.length" type="button" class="mt-8 w-full cursor-pointer rounded-md border border-[#2250A5] px-4 py-2.5 text-xs font-semibold text-[#93B8FF] transition-colors duration-200 hover:border-red-400/60 hover:bg-red-500/10 hover:text-red-300" @click="store.limpar">
            Limpar importação
          </button>
        </aside>
      </div>

      <!-- Bloco informativo: próxima etapa do projeto -->
      <section class="mt-4 rounded-xl border border-[#1A3972] bg-[#081333]/90 p-6 shadow-[0_18px_50px_rgba(0,0,0,0.12)] sm:p-8">
        <h3 class="text-sm font-semibold text-[#22D3EE]">Próxima etapa do projeto</h3>
        <p class="mt-2 text-sm leading-6 text-[#6E8DCE]">
          Após o upload, os dados serão enviados ao backend Spring Boot via Axios. O tratamento de Ciência de Dados
          ficará no Python/Pandas e a persistência no PostgreSQL/Azure.
        </p>
      </section>

      <!-- Pré-visualização -->
      <section class="mt-4 overflow-hidden rounded-xl border border-[#1A3972] bg-[#081333]/90 shadow-[0_18px_50px_rgba(0,0,0,0.12)]">
        <div class="flex flex-col justify-between gap-3 border-b border-[#172B5A] px-6 py-5 sm:flex-row sm:items-center sm:px-8">
          <div>
            <div class="flex items-center gap-3">
              <h2 class="text-base font-semibold text-white">Pré-visualização da importação</h2>
              <span v-if="store.dadosTratados.length" class="rounded-full bg-[#0C234B] px-2 py-1 text-[10px] text-[#22D3EE]">
                {{ Math.min(store.dadosTratados.length, 10) }} de {{ store.totalLinhas }}
              </span>
            </div>
            <p class="mt-1 text-xs text-[#5B80B8]">Confira os dados tratados antes de avançar.</p>
          </div>
          <span class="text-[10px] uppercase tracking-[0.18em] text-[#5B80B8]">Amostra</span>
        </div>

        <div v-if="store.dadosTratados.length" class="overflow-x-auto">
          <table class="min-w-full text-left text-xs">
            <thead class="bg-[#061536] text-[10px] uppercase tracking-wider text-[#6E8DCE]">
              <tr>
                <th class="w-14 px-6 py-3 font-medium">#</th>
                <th v-for="coluna in store.colunas" :key="coluna" class="whitespace-nowrap px-6 py-3 font-medium">{{ coluna }}</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(linha, indice) in store.dadosTratados.slice(0, 10)" :key="indice" class="border-t border-[#172B5A] text-[#93B8FF] transition-colors hover:bg-[#0A1D45]">
                <td class="px-6 py-3 text-[#5B80B8]">{{ String(indice + 1).padStart(2, '0') }}</td>
                <td v-for="coluna in store.colunas" :key="coluna" class="max-w-[240px] truncate whitespace-nowrap px-6 py-3">{{ linha[coluna] }}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div v-else class="flex min-h-[190px] flex-col items-center justify-center px-5 text-center">
          <span class="mb-3 flex h-10 w-10 items-center justify-center rounded-lg border border-[#16427C] bg-[#0C234B] text-[#315B9E]">
            <svg aria-hidden="true" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="1.5"><path stroke-linecap="round" stroke-linejoin="round" d="M4 6h16M4 10h16M4 14h10M4 18h7" /></svg>
          </span>
          <p class="text-sm text-[#6E8DCE]">Nenhum dado para visualizar ainda</p>
          <p class="mt-1 text-xs text-[#5B80B8]">Escolha uma planilha para carregar a prévia.</p>
        </div>
      </section>
    </main>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import logo from '../assets/logo.png'
import { useuploadStore } from '../store/uploadStore'

const router = useRouter()
const store = useuploadStore()
const menuAberto = ref(false)

const menuItems = [
  { label: 'Dashboard', active: false, comingSoon: true },
  { label: 'Importar dados', active: true },
  { label: 'Histórico', active: false, comingSoon: true },
  { label: 'Configurações', active: false, comingSoon: true }
]

function importarArquivo(file) {
  if (file) store.lerArquivo(file)
}

function escolherArquivo(event) {
  importarArquivo(event.target.files?.[0])
}

function soltarArquivo(event) {
  importarArquivo(event.dataTransfer.files?.[0])
}

function selecionarMenu(item) {
  if (item.label === 'Dashboard') router.push('/')
  menuAberto.value = false
}
</script>
