import { create } from 'zustand';
import type { ImportStatus } from '../types/import';

interface AppState {
  /** Currently selected import ID (for cross-page context) */
  selectedImportId: string | null;
  setSelectedImportId: (id: string | null) => void;

  /** Status filter on the dashboard */
  statusFilter: ImportStatus | 'ALL';
  setStatusFilter: (status: ImportStatus | 'ALL') => void;
}

export const useAppStore = create<AppState>((set) => ({
  selectedImportId: null,
  setSelectedImportId: (id) => set({ selectedImportId: id }),

  statusFilter: 'ALL',
  setStatusFilter: (status) => set({ statusFilter: status }),
}));
