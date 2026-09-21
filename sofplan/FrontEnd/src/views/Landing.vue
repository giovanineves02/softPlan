<template>
  <div class="relative min-h-screen overflow-hidden bg-[#030D24]">
    <div class="absolute -top-40 left-1/2 h-96 w-96 -translate-x-1/2 rounded-full bg-blue-600/20 blur-[150px]"></div>

    <div class="absolute top-40 left-1/4 h-80 w-80 rounded-full bg-cyan-500/15 blur-[180px]"></div>

    <div class="absolute top-32 right-1/4 h-72 w-72 rounded-full bg-indigo-500/20 blur-[160px]"></div>

    <div class="relative z-20">
      <Header />
    </div>

    <main class="relative z-10 flex min-h-[80vh] flex-col items-center justify-center gap-12 px-6 py-16 md:px-12">
      <section class="escritas">
        <div class="mx-auto flex max-w-4xl flex-col items-center justify-center gap-4 text-center">
          <h1 class="text-4xl font-bold tracking-tight text-white md:text-6xl">
            Gestão de planilhas
          </h1>

          <h1 class="text-4xl font-bold tracking-tight text-[#4D84F5] md:text-6xl">
            automatizada
          </h1>

          <h1 class="text-4xl font-bold tracking-tight text-white md:text-6xl">
            e dashboards inteligentes
          </h1>
          <p class="text-[#93B8FF]/70 leading-tight">
            O SOFTPLAN lê, organiza e transforma seus dados em dashboards<br>
            visuais de forma totalmente automatizada — sem complicações, sem<br>
            configuração.
          </p>
        </div>
      </section>
      <section class="w-full max-w-6xl py-12">
        <div class="mb-10 text-center">
          <p class="text-[10px] font-medium uppercase tracking-[0.25em] text-[#22D3EE]">Funcionalidades</p>
          <h2 class="mt-3 text-3xl font-bold text-white md:text-4xl">
            Tudo o que você precisa
            <span class="block font-normal text-[#6e8dce]">em um só lugar</span>
          </h2>
        </div>

        <div class="grid gap-4 md:grid-cols-2">
          <article v-for="feature in features" :key="feature.title"
            class="rounded-lg border border-[#172b5a] bg-[#081333] p-6 transition-colors duration-200 hover:border-[#2250a5]">
            <div
              class="flex h-8 w-8 items-center justify-center rounded-lg border border-[#16427c] bg-[#0c234b] text-sm text-[#22D3EE]">
              {{ feature.icon }}
            </div>
            <h3 class="mt-5 text-xs font-semibold text-white">{{ feature.title }}</h3>
            <p class="mt-2 max-w-md text-[11px] leading-relaxed text-[#6e8dce]">{{ feature.description }}</p>
          </article>
        </div>
      </section>
    </main>
    <Footer />
  </div>
</template>

<script setup>
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'

const features = [
  { icon: '▣', title: 'Leitura de Planilhas', description: 'Importação automática de dados de Excel e CSV com mapeamento inteligente de colunas e validação em tempo real.' },
  { icon: '▰', title: 'Dashboards Dinâmicos', description: 'Geração automática de painéis visuais a partir dos dados importados, com gráficos interativos e filtros avançados.' },
  { icon: '◷', title: 'Automação de Relatórios', description: 'Agendamento e distribuição automático de relatórios periódicos com notificações e exportação em múltiplos formatos.' },
  { icon: '♢', title: 'Controle de Acesso', description: 'Perfis de usuários com permissões granulares, histórico de ações e autenticação segura para toda a equipe.' },
  { icon: '▤', title: 'Multi-planilha', description: 'Gestão centralizada de múltiplas planilhas com cruzamento de dados automático e visualizações consolidadas.' },
  { icon: '∿', title: 'Monitoramento em Tempo Real', description: 'Acompanhe atualizações de dados ao vivo com alertas configuráveis e indicadores de performance instantâneos.' },
]
</script>