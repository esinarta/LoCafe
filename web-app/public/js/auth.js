$(window).on('load',function(){        
    $('#loginModal').modal('show');
     }); 


const auth = firebase.auth();

const txtEmail = document.getElementById('login-email');
const txtPassword = document.getElementById('login-pass');
const txtError = document.getElementById('error-msg');
const txtUser = document.getElementById('user-name-display');
const btnRegister = document.getElementById('register-button');
const btnLogin = document.getElementById('login-button');
const btnLogout = document.getElementById('logout-button');

btnLogin.addEventListener('click', e=>{
    //get email and password
    const email = txtEmail.value;
    const pass = txtPassword.value;
    const auth = firebase.auth();
    //sign in
    const promise = auth.signInWithEmailAndPassword(email, pass);
    promise.catch(e=> {
        console.log(e)
        txtError.innerHTML = e.message;
    });
});

btnRegister.addEventListener('click', e=>{
    //get email and password
    const email = txtEmail.value;
    const pass = txtPassword.value;
    const auth = firebase.auth();
    //sign in
    const promise = auth.createUserWithEmailAndPassword(email, pass);
    promise.catch(e=> {
        console.log(e)
        txtError.innerHTML = e.message;
    });
});

btnLogout.addEventListener('click', e=>{
    firebase.auth().signOut();
    txtError.innerHTML = '';
});

firebase.auth().onAuthStateChanged(firebaseUser =>{
    if(firebaseUser){
        console.log(firebaseUser);
        btnLogout.classList.remove('hide');
        $('#loginModal').modal('hide');
        txtUser.innerHTML = firebaseUser.email;
    }else{
        document.getElementById('root').innerHTML='';
        txtUser.innerHTML = '';
        console.log('not logged in');
        btnLogout.classList.add('hide');
    }
});