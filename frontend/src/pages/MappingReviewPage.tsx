import { useParams } from 'react-router-dom';

function MappingReviewPage() {
  const { importId } = useParams<{ importId: string }>();

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold tracking-tight">Mapping Review</h1>
        <p className="font-mono text-sm text-muted-foreground">{importId}</p>
      </div>

      <div className="rounded-md border p-8 text-center text-muted-foreground">
        Mapping proposal review UI — coming soon.
      </div>
    </div>
  );
}

export default MappingReviewPage;
