<template>
  <header class="px-6 py-4 text-white md:px-12">
    <div class="mx-auto flex max-w-7xl items-center justify-between gap-4">
      <div class="flex items-center gap-3">
        <img :src="logo" alt="Logo" class="h-10 w-10 rounded-md object-cover" />

        <div class="flex items-center">
          <h1 class="font-bold">SOFT</h1>
          <h1 class="font-bold text-[#22D3EE]">PLAN</h1>
        </div>
      </div>

      <nav>
        <ul class="flex items-center gap-6 text-lg font-semibold">
          <li><a href="#">Inicio</a></li>
          <li><a href="#">Recursos</a></li>
          <li><a href="#">Sobre</a></li>
        </ul>
      </nav>

      <button class="rounded-lg bg-[#2563EB] px-4 py-2 text-white transition hover:bg-[#22D3EE]/80">
        Entrar
      </button>
    </div>
  </header>
</template>

<script setup>
import logo from '../assets/logo.png'
</script>