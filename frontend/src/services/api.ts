import axios from 'axios';
import type { ImportSummary, ImportDetail, UploadResponse } from '../types/import';

const http = axios.create({
  baseURL: '/',
  headers: { 'Content-Type': 'application/json' },
});

/** File-upload endpoints (→ file-upload service, port 8081) */
export const uploadService = {
  uploadFile: async (file: File): Promise<UploadResponse> => {
    const formData = new FormData();
    formData.append('file', file);
    const { data } = await http.post<UploadResponse>('/api/upload/files', formData, {
      headers: { 'Content-Type': 'multipart/form-data' },
    });
    return data;
  },
};

/** Import lifecycle endpoints (→ import-interface service, port 8082) */
export const importService = {
  listImports: async (): Promise<ImportSummary[]> => {
    const { data } = await http.get<ImportSummary[]>('/api/import/imports');
    return data;
  },

  getImport: async (importId: string): Promise<ImportDetail> => {
    const { data } = await http.get<ImportDetail>(`/api/import/imports/${importId}`);
    return data;
  },
};

/** Validation endpoints (→ validation service, port 8084) */
export const validationService = {
  getResults: async (importId: string) => {
    const { data } = await http.get(`/api/validation/imports/${importId}/results`);
    return data;
  },
};

/** Mapping / publish endpoints (→ map-publish service, port 8087) */
export const publishService = {
  getProposal: async (importId: string) => {
    const { data } = await http.get(`/api/publish/imports/${importId}/proposal`);
    return data;
  },

  approve: async (importId: string) => {
    const { data } = await http.post(`/api/publish/imports/${importId}/approve`);
    return data;
  },
};
