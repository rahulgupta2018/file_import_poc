import { Routes, Route } from 'react-router-dom';
import Layout from './components/Layout';
import DashboardPage from './pages/DashboardPage';
import UploadPage from './pages/UploadPage';
import ImportDetailPage from './pages/ImportDetailPage';
import MappingReviewPage from './pages/MappingReviewPage';
import ValidationPage from './pages/ValidationPage';

function App() {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route index element={<DashboardPage />} />
        <Route path="upload" element={<UploadPage />} />
        <Route path="imports/:importId" element={<ImportDetailPage />} />
        <Route path="imports/:importId/mapping" element={<MappingReviewPage />} />
        <Route path="imports/:importId/validation" element={<ValidationPage />} />
      </Route>
    </Routes>
  );
}

export default App;
