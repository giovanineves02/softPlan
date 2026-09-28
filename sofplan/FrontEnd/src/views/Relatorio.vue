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
          <span class="hidden text-[10px] font-medium uppercase tracking-[0.22em] text-[#22D3EE] sm:block">Validação</span>
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
      <div class="mb-7 flex flex-col justify-between gap-4 sm:flex-row sm:items-end">
        <div>
          <p class="mb-3 text-[10px] font-semibold uppercase tracking-[0.28em] text-[#22D3EE]">Análise concluída</p>
          <h1 class="text-3xl font-bold tracking-tight text-white md:text-4xl">Relatório de validação</h1>
          <p class="mt-3 text-sm leading-6 text-[#6E8DCE]">Arquivo analisado: {{ store.arquivo?.name || 'Nenhum arquivo carregado' }}</p>
        </div>
        <button type="button" class="w-fit cursor-pointer rounded-md border border-[#2250A5] px-4 py-2.5 text-xs font-semibold text-[#93B8FF] transition-colors duration-200 hover:border-[#22D3EE] hover:text-[#22D3EE]" @click="router.push('/upload')">
          Voltar para importação
        </button>
      </div>

      <div v-if="!store.arquivo" class="rounded-xl border border-[#1A3972] bg-[#081333]/90 p-6 text-sm text-[#93B8FF] shadow-[0_18px_50px_rgba(0,0,0,0.12)] sm:p-8">
        Nenhuma planilha foi carregada. Volte à tela de importação para selecionar um arquivo.
      </div>

      <template v-else>
        <section class="grid gap-4 sm:grid-cols-2 xl:grid-cols-4">
          <article v-for="item in indicadores" :key="item.label" class="rounded-xl border border-[#1A3972] bg-[#081333]/90 p-6 shadow-[0_18px_50px_rgba(0,0,0,0.12)]">
            <p class="text-3xl font-bold" :class="item.cor">{{ item.valor }}</p>
            <p class="mt-2 text-xs text-[#6E8DCE]">{{ item.label }}</p>
          </article>
        </section>

        <section class="mt-4 rounded-xl border border-[#1A3972] bg-[#081333]/90 p-6 shadow-[0_18px_50px_rgba(0,0,0,0.12)] sm:p-8">
          <h2 class="mb-5 text-base font-semibold text-white">Resumo por validação</h2>
          <div v-if="Object.keys(store.errosPorTipo).length" class="overflow-x-auto">
            <table class="min-w-full text-left text-xs">
              <thead class="bg-[#061536] text-[10px] uppercase tracking-wider text-[#6E8DCE]"><tr><th class="px-4 py-3 font-medium">Validação realizada</th><th class="px-4 py-3 font-medium">Quantidade</th></tr></thead>
              <tbody>
                <tr v-for="(quantidade, tipo) in store.errosPorTipo" :key="tipo" class="border-t border-[#172B5A] text-[#93B8FF]"><td class="px-4 py-3">{{ tipo }}</td><td class="px-4 py-3">{{ quantidade }}</td></tr>
              </tbody>
            </table>
          </div>
          <p v-else class="rounded-lg border border-emerald-400/20 bg-emerald-500/10 px-4 py-3 text-sm text-emerald-300">Nenhum problema encontrado. Todos os registros estão válidos.</p>
        </section>

        <section class="mt-4 overflow-hidden rounded-xl border border-[#1A3972] bg-[#081333]/90 shadow-[0_18px_50px_rgba(0,0,0,0.12)]">
          <div class="border-b border-[#172B5A] px-6 py-5 sm:px-8">
            <h2 class="text-base font-semibold text-white">Detalhes encontrados</h2>
            <p class="mt-1 text-xs text-[#5B80B8]">Linha da planilha, campo e descrição de cada ocorrência.</p>
          </div>
          <div v-if="store.erros.length" class="max-h-[560px] overflow-auto">
            <table class="min-w-full text-left text-xs">
              <thead class="sticky top-0 bg-[#061536] text-[10px] uppercase tracking-wider text-[#6E8DCE]"><tr><th class="px-5 py-3 font-medium">Linha</th><th class="px-5 py-3 font-medium">Validação</th><th class="px-5 py-3 font-medium">Campo</th><th class="px-5 py-3 font-medium">Descrição</th></tr></thead>
              <tbody>
                <tr v-for="(item, indice) in store.erros" :key="`${item.linha}-${item.campo}-${item.tipo}-${indice}`" class="border-t border-[#172B5A] text-[#93B8FF]">
                  <td class="whitespace-nowrap px-5 py-3 text-[#22D3EE]">{{ item.linha }}</td><td class="whitespace-nowrap px-5 py-3">{{ item.tipo }}</td><td class="px-5 py-3">{{ item.campo }}</td><td class="min-w-[240px] px-5 py-3">{{ item.descricao }}</td>
                </tr>
              </tbody>
            </table>
          </div>
          <div v-else class="px-6 py-8 text-center text-sm text-[#6E8DCE]">Não há ocorrências para detalhar.</div>
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
  { label: 'Dashboard', active: false },
  { label: 'Importar dados', active: false },
  { label: 'Relatório de validação', active: true },
  { label: 'Histórico', active: false, comingSoon: true },
  { label: 'Configurações', active: false, comingSoon: true }
]
const indicadores = computed(() => [
  { label: 'Total de registros', valor: store.totalLinhas, cor: 'text-[#22D3EE]' },
  { label: 'Registros válidos', valor: store.linhasValidas, cor: 'text-emerald-300' },
  { label: 'Registros com problema', valor: store.linhasComErro, cor: 'text-rose-300' },
  { label: 'Ocorrências encontradas', valor: store.erros.length, cor: 'text-white' }
])

function selecionarMenu(item) {
  if (item.label === 'Dashboard') router.push('/dashboard')
  if (item.label === 'Importar dados') router.push('/upload')
  if (item.label === 'Relatório de validação') router.push('/relatorio')
  menuAberto.value = false
}
</script>