import { useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { uploadService } from '../services/api';

function UploadPage() {
  const navigate = useNavigate();
  const [file, setFile] = useState<File | null>(null);
  const [uploading, setUploading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleDrop = useCallback((e: React.DragEvent) => {
    e.preventDefault();
    const dropped = e.dataTransfer.files[0];
    if (dropped) setFile(dropped);
  }, []);

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const selected = e.target.files?.[0];
    if (selected) setFile(selected);
  };

  const handleUpload = async () => {
    if (!file) return;
    setUploading(true);
    setError(null);
    try {
      const result = await uploadService.uploadFile(file);
      navigate(`/imports/${result.importId}`);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Upload failed');
    } finally {
      setUploading(false);
    }
  };

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-2xl font-bold tracking-tight">Upload Payment File</h1>
        <p className="text-muted-foreground">
          Supported formats: BACS XML, Standard 18, CBO CSV, ERP CSV.
        </p>
      </div>

      <div
        className="rounded-lg border-2 border-dashed p-12 text-center"
        onDrop={handleDrop}
        onDragOver={(e) => e.preventDefault()}
      >
        {file ? (
          <div className="space-y-2">
            <p className="font-medium">{file.name}</p>
            <p className="text-sm text-muted-foreground">
              {(file.size / 1024).toFixed(1)} KB
            </p>
          </div>
        ) : (
          <div className="space-y-2">
            <p className="text-muted-foreground">
              Drag &amp; drop a payment file here, or click to browse.
            </p>
            <input
              type="file"
              onChange={handleFileChange}
              className="mx-auto block text-sm"
              accept=".xml,.csv,.txt"
            />
          </div>
        )}
      </div>

      {error && (
        <div className="rounded-md border border-destructive p-4 text-destructive text-sm">
          {error}
        </div>
      )}

      <button
        onClick={handleUpload}
        disabled={!file || uploading}
        className="rounded-md bg-primary px-4 py-2 text-sm font-medium text-primary-foreground hover:bg-primary/90 disabled:opacity-50"
      >
        {uploading ? 'Uploading...' : 'Upload & Process'}
      </button>
    </div>
  );
}

export default UploadPage;
