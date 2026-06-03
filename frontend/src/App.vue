<template>
  <div class="app">
    <header class="app-header">
      <RouterLink class="brand" to="/">Course + Work Scheduler</RouterLink>
      <nav class="nav-links" aria-label="Primary navigation">
        <RouterLink to="/">Courses</RouterLink>
        <RouterLink to="/constraints">Constraints</RouterLink>
        <RouterLink to="/schedules">Schedules</RouterLink>
        <RouterLink to="/saved-schedules">Saved</RouterLink>
      </nav>
    </header>

    <main>
      <RouterView />
    </main>
  </div>
</template>

<style scoped>
.app {
  min-height: 100vh;
  color: #172033;
  background: #f4f7fb;
  font-family:
    Inter, ui-sans-serif, system-ui, -apple-system, BlinkMacSystemFont, "Segoe UI", sans-serif;
}

.app-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;
  border-bottom: 1px solid #dbe1ea;
  padding: 1rem 2rem;
  background: #ffffff;
}

.brand {
  color: #111827;
  font-size: 1rem;
  font-weight: 900;
  text-decoration: none;
}

.nav-links {
  display: flex;
  gap: 0.5rem;
}

.nav-links a {
  border-radius: 6px;
  padding: 0.55rem 0.75rem;
  color: #415066;
  font-weight: 800;
  text-decoration: none;
}

.nav-links a.router-link-active {
  color: #17407c;
  background: #dbeafe;
}

@media (max-width: 640px) {
  .app-header {
    align-items: flex-start;
    flex-direction: column;
    padding: 1rem;
  }
}
</style>
