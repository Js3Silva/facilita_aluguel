document.getElementById("rowsPerPage").addEventListener("change", function () {
  alert("Você selecionou " + this.value + " linhas por página.");
});

document.addEventListener("DOMContentLoaded", () => {
  const tabelaBody = document.querySelector("table tbody");
  var idCliente = localStorage.getItem("clienteId");
  console.log(idCliente);
  fetch(`http://localhost:8080/pedidos/clientes/${idCliente}`)
      .then(response => response.json())
      .then(pedidos => {
        tabelaBody.innerHTML = "";
        pedidos.forEach(pedido => {
          const row = document.createElement("tr");
          row.innerHTML = `
                    <td>${pedido.id}</td>
                    <td>${pedido.cliente.nome}</td>
                    <td>${pedido.automovel.modelo}</td>
                    <td>${new Date(pedido.dataInicio).toLocaleDateString()}</td>
                    <td>${new Date(pedido.dataFim).toLocaleDateString()}</td>
                    <td class="text-center">
                        <button class="btn btn-sm btn-primary me-2" onclick="verPedido(${pedido.id})">Ver</button>
                        <button class="btn btn-sm btn-danger" onclick="cancelarPedido(${pedido.id})">Cancelar</button>
                    </td>
                `;
          tabelaBody.appendChild(row);
        });
      })
      .catch(error => console.error("Erro ao carregar pedidos:", error));
});

function verPedido(id) {
  window.location.href = `detalhes-pedido.html?id=${id}`;
}

function cancelarPedido(id) {
  if (confirm("Tem certeza que deseja cancelar este pedido?")) {
    fetch(`http://localhost:8080/pedidos/cancelar/${id}`, { method: "POST" })
        .then(response => response.json())
        .then(() => alert("Pedido cancelado com sucesso!"))
        .catch(error => console.error("Erro ao cancelar pedido:", error));
  }
}
