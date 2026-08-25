<template>
  <footer class="bg-transparent px-4 py-8 text-[#0051ff] md:px-12">
    <div class="mx-auto flex max-w-7xl flex-col items-center justify-between gap-6 md:flex-row">
      <p class="text-center text-base text-[#6e8dce]">
        &copy; 2023 SOFTPLAN. Todos os direitos reservados.
      </p>

      <div class="flex items-center gap-4">
        <a :href="instagramUrl" target="_blank" rel="noopener noreferrer" aria-label="Instagram da SOFTPLAN"
          class="rounded-lg p-1 text-[#6e8dce] transition-colors duration-200 hover:text-[#22D3EE]">
          <svg aria-hidden="true" class="h-8 w-8" viewBox="0 0 24 24" fill="none" stroke="currentColor"
            stroke-width="1.8">
            <rect x="3" y="3" width="18" height="18" rx="5" />
            <circle cx="12" cy="12" r="4" />
            <circle cx="17.5" cy="6.5" r="1" fill="currentColor" stroke="none" />
          </svg>
        </a>

        <a :href="whatsappUrl" target="_blank" rel="noopener noreferrer" aria-label="WhatsApp da SOFTPLAN"
          class="rounded-lg p-1 text-[#6e8dce] transition-colors duration-200 hover:text-[#22D3EE]">
          <svg aria-hidden="true" class="h-8 w-8" viewBox="0 0 24 24" fill="currentColor">
            <path
              d="M12 2.5a9.5 9.5 0 0 0-8.23 14.25L2.5 21.5l4.9-1.25A9.5 9.5 0 1 0 12 2.5Zm0 17a7.48 7.48 0 0 1-3.82-1.05l-.28-.17-2.91.74.78-2.83-.18-.29A7.5 7.5 0 1 1 12 19.5Zm4.1-5.58c-.23-.12-1.35-.67-1.56-.74-.21-.08-.36-.12-.51.12-.15.23-.59.74-.72.89-.13.16-.26.17-.49.06-.23-.12-.97-.36-1.85-1.15-.68-.61-1.15-1.36-1.28-1.59-.13-.23-.01-.36.1-.48.11-.11.23-.26.35-.39.12-.13.16-.23.23-.39.08-.15.04-.29-.02-.41-.06-.12-.51-1.23-.7-1.69-.18-.44-.37-.38-.51-.39h-.43c-.15 0-.39.06-.6.29-.21.23-.79.77-.79 1.88s.81 2.18.92 2.33c.12.16 1.59 2.43 3.85 3.4.54.23.96.37 1.29.47.54.17 1.04.15 1.43.09.44-.07 1.35-.55 1.54-1.08.19-.53.19-.98.13-1.08-.05-.09-.21-.15-.44-.27Z" />
          </svg>
        </a>
      </div>
    </div>
  </footer>
</template>

<script setup>
const instagramUrl = 'https://www.instagram.com/'
const whatsappUrl = 'https://wa.me/5500000000000'
</script>
