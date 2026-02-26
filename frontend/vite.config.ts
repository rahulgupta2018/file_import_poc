import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import path from 'path';

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src'),
    },
  },
  server: {
    port: 5173,
    proxy: {
      '/api/upload': {
        target: 'http://localhost:8081',
        changeOrigin: true,
      },
      '/api/import': {
        target: 'http://localhost:8082',
        changeOrigin: true,
      },
      '/api/rules': {
        target: 'http://localhost:8083',
        changeOrigin: true,
      },
      '/api/validation': {
        target: 'http://localhost:8084',
        changeOrigin: true,
      },
      '/api/agent': {
        target: 'http://localhost:8086',
        changeOrigin: true,
      },
      '/api/publish': {
        target: 'http://localhost:8087',
        changeOrigin: true,
      },
      '/api/llm': {
        target: 'http://localhost:8088',
        changeOrigin: true,
      },
    },
  },
});
