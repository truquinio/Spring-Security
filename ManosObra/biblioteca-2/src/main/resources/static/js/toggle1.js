var toggle = document.getElementById('darkmode');
var body = document.querySelector('body');
var nav = document.getElementById('navbar');
var list1 = document.getElementById('lista1');
var list2 = document.getElementById('lista2');
var list3 = document.getElementById('lista3');
var list4 = document.getElementById('lista4');
var list5 = document.getElementById('lista5');
var main0 = document.querySelector('main0');
var main = document.querySelector('main');
var main2 = document.querySelector('main2');
var main3 = document.querySelector('main3');
var footer = document.querySelector('footer');
var sky = document.querySelector('sky');


toggle.onclick = function () {
  toggle.classList.toggle('active');
  body.classList.toggle('active');
  nav.classList.toggle('active');
  list1.classList.toggle('active');
  list2.classList.toggle('active');
  list3.classList.toggle('active');
  list4.classList.toggle('active');
  list5.classList.toggle('active');
  main.classList.toggle('active');
  main2.classList.toggle('active');
  main3.classList.toggle('active');
  footer.classList.toggle('active');
  sky.classList.toggle('active');
}

  /*if (document.body.classList.add('dark')){

  document.body.classList.contains('dark');
  document.body.classList.remove('dark');

  }else {
    document.body.classList.add('dark');
  }*/