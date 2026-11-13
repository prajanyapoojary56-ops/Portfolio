const list = document.getElementById("projects");

// 1. Load projects from the Spring Boot API
async function loadProjects() {
  try {
    const res = await fetch("/api/projects");
    if (!res.ok) throw new Error("Bad response");
    renderProjects(await res.json());
  } catch (err) {
    list.innerHTML = "";
    const li = document.createElement("li");
    li.textContent = "Could not load projects. Check that the Spring Boot server is running.";
    list.appendChild(li);
  }
}

function renderProjects(projects) {
  list.innerHTML = "";
  projects.forEach(p => {
    const li = document.createElement("li");

    if (p.imageUrl) {
      const img = document.createElement("img");
      img.src = p.imageUrl;
      img.alt = `Screenshot of ${p.title}`;
      img.loading = "lazy";
      li.appendChild(img);
    }

    const h3 = document.createElement("h3");
    h3.textContent = p.title;
    li.appendChild(h3);

    if (p.category) {
      const kind = document.createElement("p");
      kind.className = "kind";
      kind.textContent = p.category;
      li.appendChild(kind);
    }

    const desc = document.createElement("p");
    desc.textContent = p.description;
    li.appendChild(desc);

    const stack = document.createElement("span");
    stack.className = "stack";
    stack.textContent = "Technologies: " + p.stack;
    li.appendChild(stack);

    // Links only appear when a real URL has been added
    const links = document.createElement("div");
    links.className = "links";
    [["GitHub", p.codeUrl], ["Live demo", p.liveUrl]].forEach(([label, url]) => {
      if (!url || url === "#") return;
      const a = document.createElement("a");
      a.textContent = label;
      a.href = url;
      links.appendChild(a);
    });
    if (links.children.length) li.appendChild(links);

    list.appendChild(li);
  });
}

// 2. Dark / light theme, remembered between visits
const root = document.documentElement;
const themeBtn = document.getElementById("theme");

function setTheme(mode) {
  root.dataset.theme = mode;
  themeBtn.textContent = mode === "dark" ? "Light" : "Dark";
  themeBtn.setAttribute("aria-label", `Switch to ${mode === "dark" ? "light" : "dark"} theme`);
  try { localStorage.setItem("theme", mode); } catch (e) {}
}

let saved = null;
try { saved = localStorage.getItem("theme"); } catch (e) {}
setTheme(saved || (matchMedia("(prefers-color-scheme: dark)").matches ? "dark" : "light"));
themeBtn.addEventListener("click", () => setTheme(root.dataset.theme === "dark" ? "light" : "dark"));

// 3. Contact form: validate, then POST to the API (saved with Hibernate)
const form = document.getElementById("form");
const result = document.getElementById("result");

form.addEventListener("submit", async e => {
  e.preventDefault();
  const fields = ["name", "email", "msg"].map(id => document.getElementById(id));
  let valid = true;
  fields.forEach(f => {
    const ok = f.value.trim() !== "" && (f.type !== "email" || /^\S+@\S+\.\S+$/.test(f.value));
    f.setAttribute("aria-invalid", String(!ok));
    if (!ok) valid = false;
  });
  if (!valid) {
    result.textContent = "Please fill in every field with a valid email address.";
    return;
  }

  const [name, email, msg] = fields;
  try {
    const res = await fetch("/api/contact", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ name: name.value.trim(), email: email.value.trim(), message: msg.value.trim() })
    });
    if (!res.ok) throw new Error("Request failed");
    result.textContent = "Thanks! Your message was sent.";
    form.reset();
  } catch (err) {
    result.textContent = "Something went wrong. Please try again.";
  }
});

loadProjects();
