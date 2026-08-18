<template>
  <header class="bg-transparent px-6 py-4 text-white md:px-12">
    <div class="mx-auto flex max-w-7xl items-center justify-between gap-4">
      <div class="flex items-center gap-3">
        <img :src="logo" alt="Logo" class="h-10 w-10 rounded-md object-cover" />

        <div class="flex items-center">
          <h1 class="font-bold">SOFT</h1>
          <h1 class="font-bold text-[#22D3EE]">PLAN</h1>
        </div>
      </div>

      <nav>
        <ul class="flex items-center gap-4 text-lg font-semibold">
          <li>
            <button
              type="button"
              class="inline-flex cursor-pointer items-center justify-center rounded-lg border border-white/15 bg-transparent px-4 py-2 font-semibold text-white transition-all duration-200 hover:-translate-y-0.5 hover:border-[#22D3EE] hover:bg-[#22D3EE] hover:text-[#030D24] hover:shadow-[0_0_20px_rgba(34,211,238,0.6)] active:translate-y-0"
            >
              Inicio
            </button>
          </li>
          <li>
            <button
              type="button"
              class="inline-flex cursor-pointer items-center justify-center rounded-lg border border-white/15 bg-transparent px-4 py-2 font-semibold text-white transition-all duration-200 hover:-translate-y-0.5 hover:border-[#22D3EE] hover:bg-[#22D3EE] hover:text-[#030D24] hover:shadow-[0_0_20px_rgba(34,211,238,0.6)] active:translate-y-0"
            >
              Recursos
            </button>
          </li>
          <li>
            <button
              type="button"
              class="inline-flex cursor-pointer items-center justify-center rounded-lg border border-white/15 bg-transparent px-4 py-2 font-semibold text-white transition-all duration-200 hover:-translate-y-0.5 hover:border-[#22D3EE] hover:bg-[#22D3EE] hover:text-[#030D24] hover:shadow-[0_0_20px_rgba(34,211,238,0.6)] active:translate-y-0"
            >
              Sobre
            </button>
          </li>
        </ul>
      </nav>

      <button class="cursor-pointer rounded-lg bg-[#2563EB] px-4 py-2 font-semibold text-white transition-all duration-200 hover:-translate-y-0.5 hover:bg-[#22D3EE] hover:text-[#030D24] hover:shadow-[0_0_24px_rgba(34,211,238,0.7)] active:translate-y-0">
        Entrar
      </button>
    </div>
  </header>
</template>

<script setup>
import logo from '../assets/logo.png'
</script>