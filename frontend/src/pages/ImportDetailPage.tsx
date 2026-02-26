import { useParams } from 'react-router-dom';
import { useQuery } from '@tanstack/react-query';
import { importService } from '../services/api';
import type { ImportDetail } from '../types/import';

function ImportDetailPage() {
  const { importId } = useParams<{ importId: string }>();

  const { data: detail, isLoading } = useQuery<ImportDetail>({
    queryKey: ['import', importId],
    queryFn: () => importService.getImport(importId!),
    enabled: !!importId,
  });

  if (isLoading) {
    return <div className="text-muted-foreground">Loading import details...</div>;
  }

  if (!detail) {
    return <div className="text-destructive">Import not found.</div>;
  }

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold tracking-tight">Import Detail</h1>
        <p className="font-mono text-sm text-muted-foreground">{detail.importId}</p>
      </div>

      <div className="grid gap-4 md:grid-cols-3">
        <div className="rounded-md border p-4">
          <p className="text-sm text-muted-foreground">Status</p>
          <p className="text-lg font-semibold">{detail.status}</p>
        </div>
        <div className="rounded-md border p-4">
          <p className="text-sm text-muted-foreground">Source Type</p>
          <p className="text-lg font-semibold">{detail.sourceType}</p>
        </div>
        <div className="rounded-md border p-4">
          <p className="text-sm text-muted-foreground">Entries</p>
          <p className="text-lg font-semibold">{detail.entryCount}</p>
        </div>
      </div>

      <div className="flex gap-3">
        <a
          href={`/imports/${importId}/mapping`}
          className="rounded-md bg-secondary px-4 py-2 text-sm font-medium hover:bg-secondary/80"
        >
          Review Mapping
        </a>
        <a
          href={`/imports/${importId}/validation`}
          className="rounded-md bg-secondary px-4 py-2 text-sm font-medium hover:bg-secondary/80"
        >
          View Validation
        </a>
      </div>
    </div>
  );
}

export default ImportDetailPage;
