import { Outlet, NavLink } from 'react-router-dom';

function Layout() {
  return (
    <div className="min-h-screen flex flex-col">
      {/* Header */}
      <header className="border-b bg-card">
        <div className="container flex h-14 items-center gap-6">
          <NavLink to="/" className="font-semibold text-lg">
            File Import Automation
          </NavLink>
          <nav className="flex gap-4 text-sm">
            <NavLink
              to="/"
              end
              className={({ isActive }) =>
                isActive ? 'text-primary font-medium' : 'text-muted-foreground hover:text-foreground'
              }
            >
              Dashboard
            </NavLink>
            <NavLink
              to="/upload"
              className={({ isActive }) =>
                isActive ? 'text-primary font-medium' : 'text-muted-foreground hover:text-foreground'
              }
            >
              Upload
            </NavLink>
          </nav>
        </div>
      </header>

      {/* Main content */}
      <main className="container flex-1 py-6">
        <Outlet />
      </main>

      {/* Footer */}
      <footer className="border-t py-4">
        <div className="container text-center text-xs text-muted-foreground">
          File Import Automation &mdash; Dev Environment
        </div>
      </footer>
    </div>
  );
}

export default Layout;
