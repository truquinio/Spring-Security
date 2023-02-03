const toggleButton = document.querySelector("#dark-mode-toggle");

//Obtiene el estado actual del modo oscuro desde localStorage
const currentDarkModeSetting = localStorage.getItem("darkMode");

//Establece el estado actual del modo oscuro en la página
document.body.classList.toggle("dark-mode", currentDarkModeSetting === "enabled");

//Escucha el clic en el botón y alterna el estado del modo oscuro
toggleButton.addEventListener("click", () => {
  document.body.classList.toggle("dark-mode");
  localStorage.setItem("darkMode", document.body.classList.contains("dark-mode") ? "enabled" : "disabled");
});
    