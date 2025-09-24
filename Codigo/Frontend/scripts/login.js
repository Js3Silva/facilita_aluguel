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