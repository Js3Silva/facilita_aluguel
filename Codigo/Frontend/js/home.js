document.addEventListener("DOMContentLoaded", () => {
  const container = document.getElementById("carros-container");
  const modal = new bootstrap.Modal(document.getElementById("modalAluguel"));

  let carroSelecionado = null; // Armazena dados do carro clicado

  // 1. Buscar lista de carros e montar cards
  fetch("/api/carros") // <- sua API de carros
    .then(res => res.json())
    .then(carros => {
      container.innerHTML = "";
      carros.forEach(carro => {
        const card = document.createElement("div");
        card.classList.add("col-md-3", "col-sm-6", "mb-3");
        card.innerHTML = `
          <div class="card">
            <img src="${carro.imagem}" class="card-img-top">
            <div class="card-body text-center">
              <h5>${carro.marca}</h5>
              <p class="text-muted">${carro.modelo}</p>
              <p class="fw-bold">R$${carro.preco}</p>
              <button class="btn btn-primary btnAlugar">Alugar</button>
            </div>
          </div>
        `;

        // Evento do botão "Alugar"
        card.querySelector(".btnAlugar").addEventListener("click", () => {
          // 2. Buscar detalhes do carro clicado
          fetch(`/api/carros/${carro.id}`)
            .then(res => res.json())
            .then(detalhes => {
              carroSelecionado = detalhes;

              // Preenche modal
              document.getElementById("modalTitulo").textContent = detalhes.marca;
              document.getElementById("modalImg").src = detalhes.imagem;
              document.getElementById("modalDesc").textContent = detalhes.descricao;
              document.getElementById("modalPreco").textContent = `R$${detalhes.preco}`;

              modal.show();
            });
        });

        container.appendChild(card);
      });
    });

  // 3. Confirmar pedido
  document.getElementById("btnConfirmar").addEventListener("click", () => {
    if (!carroSelecionado) return;

    fetch("/api/pedidos", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ carroId: carroSelecionado.id })
    })
      .then(res => res.json())
      .then(resp => {
        alert("Pedido confirmado com sucesso!");
        modal.hide();
      })
      .catch(() => alert("Erro ao confirmar pedido."));
  });
});
