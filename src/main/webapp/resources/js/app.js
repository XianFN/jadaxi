

var app = new Vue({
  el: '#app',
  data: {
    message: "Francisco Javier Alvarez de Celis",
    counter: 0,
    date: "",
    selected: '',
    htmlCheked: "",
    favouriteColor:"",
    updated: true,
    msg: 'El color de esta línea debe cambiar a rojo después de 5 segundos, y debe cambiar este texto a:"¡Ha cambiado!"',

  },

  
  computed: {

    age: function () { //Funcion para restar las fechas 

      return moment(Date.now()).diff(moment(this.date), 'years')
    },

    colorLeft: function () {//FUncion para cambiar el color al seleccionar opciones en un comboBox

      if (this.selected == "hombre") {

        return "rgb(19, 5, 219)";

      } else if(this.selected == "mujer") {
        return "rgb(125, 5, 69)";
      }else{
        return "rgb(47,79,79)";
      }
    },

    hideImage: function () {//funcion para ocultar la imagen al leer el checkBox del html TODO preguntar si hay alguna forma de hacer que sea void

      console.log(this.htmlCheked);
      var x = document.getElementById("image");

      if (this.htmlCheked == false) {
        x.style.display = "block";


      } else {
        x.style.display = "none";

      }

      
    },

    colorRight: function(){

      console.log(this.favouriteColor);

      return this.favouriteColor;

    }
  },
  methods: {

    cambiaColor: function() {

      this.updated = !this.updated;
    },
     
  cambiaTexto: function() {

    if(!this.updated) {
      this.msg = '¡Ha cambiado!';

    }
  }

  },

  created:function(){

    Vue.component('aw', {
      props: ['post'],
      data: function () {
        return {
          titulo:'AW',
          texto:'Practica de Francisco Javier Alvarez',
        }
      },
      template: '<div> <h1>{{titulo}}</h1> <p>{{texto}}</p></div>'
    })
      setTimeout(() => this.cambiaColor() , 5000);
      setTimeout(() => this.cambiaTexto(), 7000);
  
  }
})



