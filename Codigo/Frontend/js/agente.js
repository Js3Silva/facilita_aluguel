document.getElementById("rowsPerPage").addEventListener("change", function () {
  alert("O agente configurou para mostrar " + this.value + " pedidos por página.");
});

// Ações de Aprovar/Rejeitar
document.querySelectorAll(".btn-success").forEach(btn => {
  btn.addEventListener("click", () => {
    alert("Pedido aprovado com sucesso!");
  });
});

document.querySelectorAll(".btn-danger").forEach(btn => {
  btn.addEventListener("click", () => {
    alert("Pedido rejeitado.");
  });
});
