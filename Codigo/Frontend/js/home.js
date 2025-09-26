document.addEventListener("DOMContentLoaded", () => {
  const container = document.getElementById("carros-container");
  const modal = new bootstrap.Modal(document.getElementById("modalAluguel"));

  let carroSelecionado = null; 

  fetch("http://localhost:8080/automoveis/all") 
    .then(res => res.json())
    .then(carros => {
      container.innerHTML = "";
      carros.forEach(carro => {
        const card = document.createElement("div");
        card.classList.add("col-md-3", "col-sm-6", "mb-3");
        card.innerHTML = `
          <div class="card">
            <img src="../assets/suv.png" class="card-img-top">
            <div class="card-body text-center">
              <h5>${carro.marca}</h5>
              <p class="text-muted">${carro.modelo}</p>
              <p class="fw-bold">R$500</p>
              <button class="btn btn-primary btnAlugar">Alugar</button>
            </div>
          </div>
        `;


        card.querySelector(".btnAlugar").addEventListener("click", () => {
          fetch(`http://localhost:8080/automoveis/${carro.id}`)
            .then(res => res.json())
            .then(detalhes => {
              carroSelecionado = detalhes;

              document.getElementById("modalTitulo").textContent = detalhes.modelo;
              document.getElementById("modalModelo").textContent = detalhes.marca;
              document.getElementById("modalAno").textContent = detalhes.ano;
              document.getElementById("modalPlaca").textContent = detalhes.placa;
              document.getElementById("modalMatricula").textContent = detalhes.matricula;
              document.getElementById("modalPreco").textContent = "500";
              document.getElementById("modalImg").src = "../assets/suv.png";

              modal.show();
            });
        });

        container.appendChild(card);
      });
    });

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

  document.addEventListener("DOMContentLoaded", () => {
    if (!localStorage.getItem("automoveis_inicializados")) {
      
      const automoveisMock = [
        {
          matricula: 10,
          ano: 2025,
          marca: "Toyota",
          placa: "ABC1234",
          modelo: "Corolla",
          situacao: "DISPONIVEL"
        },
        {
          matricula: 2,
          ano: 2019,
          marca: "Honda",
          placa: "XYZ5678",
          modelo: "Civic",
          situacao: "DISPONIVEL"
        },
        {
          matricula: 3,
          ano: 2021,
          marca: "Ford",
          placa: "DEF5678",
          modelo: "Mustang",
          situacao: "DISPONIVEL"
        },
        {
          matricula: 4,
          ano: 2019,
          marca: "Volkswagen",
          placa: "XYZ5228",
          modelo: "Gol",
          situacao: "DISPONIVEL"
        }
      ];

      // Faz a requisição POST para cada veículo
      automoveisMock.forEach(auto => {
        fetch("http://localhost:8080/automoveis/cadastrar", {
          method: "POST",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify(auto)
        })
        .then(res => {
          if (!res.ok) throw new Error("Erro ao cadastrar veículo");
          return res.json();
        })
        .then(data => console.log("Criado:", data))
        .catch(err => console.error(err));
      });

      localStorage.setItem("automoveis_inicializados", "true");
    }
  });
