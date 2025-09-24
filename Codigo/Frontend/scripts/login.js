const btnToReg = document.querySelector('#to-register')
const btnToLog = document.querySelector('#to-login')
const modalLogin = document.querySelector('.login-container')
const modalRegister = document.querySelector('.register-container')

var displayNone = 'disp_none'


 btnToReg.addEventListener('click', () =>{
    modalLogin.classList.add(displayNone)
    modalRegister.classList.remove(displayNone)
 })

  btnToLog.addEventListener('click', () =>{
    modalRegister.classList.add(displayNone)
    modalLogin.classList.remove(displayNone)
 })


 id="check-cliente"

const checkCliente = document.querySelector('#check-cliente')
const labelCliente = document.querySelector('#label-cliente')
const checkAgente = document.querySelector('#check-agente')
const labelAgente = document.querySelector('#label-agente')




labelCliente.addEventListener('click', () => {
    checkCliente.checked = true;
    checkAgente.checked = false; 
    labelCliente.classList.add('bg-red')
    labelAgente.classList.remove('bg-red')
})

labelAgente.addEventListener('click', () => {
    checkAgente.checked = true;
    checkCliente.checked = false; 
    labelAgente.classList.add('bg-red')
    labelCliente.classList.remove('bg-red')
})