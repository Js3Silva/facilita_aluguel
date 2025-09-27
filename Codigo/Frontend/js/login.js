document.addEventListener("DOMContentLoaded", () => {

    const btnToReg = document.querySelector('#to-register');
    const btnToLog = document.querySelector('#to-login');
    const modalLogin = document.querySelector('.login-container');
    const modalRegister = document.querySelector('.register-container');
    const displayNone = 'disp_none';

    btnToReg.addEventListener('click', () => {
        modalLogin.classList.add(displayNone);
        modalRegister.classList.remove(displayNone);
    });

    btnToLog.addEventListener('click', () => {
        modalRegister.classList.add(displayNone);
        modalLogin.classList.remove(displayNone);
    });

    const checkCliente = document.querySelector('#check-cliente');
    const labelCliente = document.querySelector('#label-cliente');
    const checkAgente = document.querySelector('#check-agente');
    const labelAgente = document.querySelector('#label-agente');

    if (labelCliente && labelAgente) {
        labelCliente.addEventListener('click', () => {
            checkCliente.checked = true;
            checkAgente.checked = false;
            labelCliente.classList.add('bg-red');
            labelAgente.classList.remove('bg-red');
        });

        labelAgente.addEventListener('click', () => {
            checkAgente.checked = true;
            checkCliente.checked = false;
            labelAgente.classList.add('bg-red');
            labelCliente.classList.remove('bg-red');
        });
    }

    // --- LOGIN ---
    const btnLogin = document.querySelector("#btn-login");
    if (btnLogin) {
        btnLogin.addEventListener("click", async () => {
            const email = document.querySelector("#login-email").value;
            const senha = document.querySelector("#login-senha").value;

            try {
                const resp = await fetch("http://localhost:8080/clientes/login", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ email, senha })
                });

                if (!resp.ok) {
                    const msg = await resp.json();
                    alert("Erro no login: " + msg);
                    return;
                }

                const cliente = await resp.json();
                localStorage.setItem("clienteId", cliente.id);
                localStorage.setItem("usuarioLogado", "true");

                alert("Login realizado com sucesso!");
                window.location.href = "../index.html";
            } catch (err) {
                alert("Erro de conexão: " + err.message);
            }
        });
    }

    // --- CADASTRO ---
    const btnRegister = document.querySelector("#btn-register");
    if (btnRegister) {
        btnRegister.addEventListener("click", async () => {
            const cliente = {
                nome: document.querySelector("#reg-nome").value,
                email: document.querySelector("#reg-email").value,
                senha: document.querySelector("#reg-senha").value,
                profissao: document.querySelector("#reg-profissao").value,
                cpf: document.querySelector("#reg-cpf").value,
                rg: document.querySelector("#reg-rg").value,
                endereco: {
                    logradouro: document.querySelector("#reg-logradouro").value,
                    complemento: document.querySelector("#reg-complemento").value,
                    estado: document.querySelector("#reg-estado").value,
                    cidade: document.querySelector("#reg-cidade").value,
                    bairro: document.querySelector("#reg-bairro").value,
                    numero: document.querySelector("#reg-numero").value,
                    cep: document.querySelector("#reg-cep").value
                },
                rendimentos: [
                    {
                        tipoRendimento: document.querySelector("#reg-tipo-rendimento").value,
                        valorMensal: parseFloat(document.querySelector("#reg-rendimento").value)
                    }
                ]
            };

            try {
                const resp = await fetch("http://localhost:8080/clientes/cadastrar", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(cliente)
                });

                if (!resp.ok) {
                    const msg = await resp.text();
                    alert("Erro no cadastro: " + msg);
                    return;
                }

                alert("Cadastro realizado com sucesso! Faça login.");
                document.querySelector("#to-login").click();
            } catch (err) {
                alert("Erro de conexão: " + err.message);
            }
        });
    }

});
