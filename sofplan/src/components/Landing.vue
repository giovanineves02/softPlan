<template>
  <div class="relative min-h-screen overflow-hidden bg-[#030D24]">
    <div
      class="absolute -top-40 left-1/2 h-96 w-96 -translate-x-1/2 rounded-full bg-blue-600/20 blur-[150px]"
    ></div>

    <div
      class="absolute top-40 left-1/4 h-80 w-80 rounded-full bg-cyan-500/15 blur-[180px]"
    ></div>

    <div
      class="absolute top-32 right-1/4 h-72 w-72 rounded-full bg-indigo-500/20 blur-[160px]"
    ></div>

    <div class="relative z-20">
      <Header />
    </div>

    <main class="relative z-10 flex min-h-[80vh] items-center justify-center px-6 md:px-12">
      <div
        class="mx-auto flex max-w-4xl flex-col items-center justify-center gap-4 text-center"
      >
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
    </main>
  </div>
</template>

<script setup>
import Header from './Header.vue'
</script>