import { useQuery } from '@tanstack/react-query';
import { importService } from '../services/api';
import type { ImportSummary } from '../types/import';

function DashboardPage() {
  const { data: imports, isLoading, error } = useQuery<ImportSummary[]>({
    queryKey: ['imports'],
    queryFn: importService.listImports,
  });

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold tracking-tight">Dashboard</h1>
        <p className="text-muted-foreground">Recent file import activity.</p>
      </div>

      {isLoading && (
        <div className="text-muted-foreground">Loading imports...</div>
      )}

      {error && (
        <div className="rounded-md border border-destructive p-4 text-destructive">
          Failed to load imports.
        </div>
      )}

      {imports && imports.length === 0 && (
        <div className="rounded-md border p-8 text-center text-muted-foreground">
          No imports yet. Upload a payment file to get started.
        </div>
      )}

      {imports && imports.length > 0 && (
        <div className="rounded-md border">
          <table className="w-full text-sm">
            <thead>
              <tr className="border-b bg-muted/50">
                <th className="px-4 py-3 text-left font-medium">Import ID</th>
                <th className="px-4 py-3 text-left font-medium">File Name</th>
                <th className="px-4 py-3 text-left font-medium">Source Type</th>
                <th className="px-4 py-3 text-left font-medium">Status</th>
                <th className="px-4 py-3 text-left font-medium">Created</th>
              </tr>
            </thead>
            <tbody>
              {imports.map((imp) => (
                <tr key={imp.importId} className="border-b hover:bg-muted/25">
                  <td className="px-4 py-3 font-mono text-xs">
                    <a
                      href={`/imports/${imp.importId}`}
                      className="text-primary underline-offset-4 hover:underline"
                    >
                      {imp.importId.slice(0, 8)}
                    </a>
                  </td>
                  <td className="px-4 py-3">{imp.fileName}</td>
                  <td className="px-4 py-3">{imp.sourceType}</td>
                  <td className="px-4 py-3">
                    <span className="rounded-full bg-secondary px-2.5 py-0.5 text-xs font-medium">
                      {imp.status}
                    </span>
                  </td>
                  <td className="px-4 py-3 text-muted-foreground">{imp.createdAt}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

export default DashboardPage;
