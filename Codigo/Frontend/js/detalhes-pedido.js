const urlParams = new URLSearchParams(window.location.search);
const pedidoId = urlParams.get('id');

if (!pedidoId) {
    alert("ID do pedido não fornecido");
}

function preencherPedido(pedido, automovel) {

    console.log(pedido);
    console.log(automovel);

    document.getElementById("pedidoId").textContent = pedido.id;
    document.getElementById("dataCriacao").textContent = new Date(pedido.dataCriacao).toLocaleDateString();
    document.getElementById("dataInicio").textContent = new Date(pedido.dataInicio).toLocaleDateString();
    document.getElementById("dataFim").textContent = new Date(pedido.dataCriacao).toLocaleDateString();
    document.getElementById("status").textContent = pedido.status;

    document.getElementById("matricula").textContent = automovel.matricula;
    document.getElementById("ano").textContent = automovel.ano;
    document.getElementById("marca").textContent = automovel.marca;
    document.getElementById("placa").textContent = automovel.placa;
    document.getElementById("modelo").textContent = automovel.modelo;
}

fetch(`http://localhost:8080/pedidos/${pedidoId}`)
    .then(res => res.json())
    .then(pedido => {
        const automovelPromise = fetch(`http://localhost:8080/automoveis/${pedido.automovelId}`)
            .then(res => res.json());

        return Promise.all([Promise.resolve(pedido), automovelPromise]);
    })
    .then(([pedido, automovel]) => {
        preencherPedido(pedido, automovel);
    })
    .catch(err => {
        console.error(err);
        alert("Erro ao carregar o pedido");
    });

document.getElementById("cancelarPedidoBtn").addEventListener("click", () => {
    fetch(`http://localhost:8080/pedidos/cancelar/${pedidoId}`, {
        method: "POST"
    })
        .then(res => {
            if (res.ok) {
                alert("Pedido cancelado com sucesso!");
                window.location.href = "pedidos.html";
            } else {
                alert("Erro ao cancelar pedido");
            }
        })
        .catch(err => {
            console.error(err);
            alert("Erro ao cancelar pedido");
        });
});