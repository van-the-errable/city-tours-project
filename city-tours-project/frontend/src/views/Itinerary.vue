<template>
    <div class="tile is-ancestor is-vertical is-mobile">
      <div class="content"
      <p id="itinerary-title"> Your Itinerary </p>
  <!-- for each landmark -->
      <div class="tile is-parent is-11" id="itinerary-item">  
          <div class="content"
          <p> 1.  </p>  
         <article class="tile is-child">
             <p class="title">Starting Point</p>
             <p class="subtitle">Renaissance Hotel</p>
             <div class="content"
             <p>The Renaissance Hotel is really nice and I hope you like it. Blah blah blah.</p>
          </article>
      <!-- </div>
      <div class="tile is-parent" id="itinerary-image"> -->
          <article class="tile is-child">
              <figure class="image is-2by1">
                  <img src="../img/hotel_img.jpg">
              </figure>
          </article>
      </div>
  
      <div class="tile is-parent is-10 centered" id="route-item">  
         <article class="tile is-child">
             <p class="title">Route</p>
             <div class="content"
             <p id="directions">1. Exit the Hotel and Turn Right on 6th Street.</p>
             <p id="directions">2. Continue on 6th Street and cross the Roberto Clemente Bridge.</p>
             <p id="directions">3. Turn Right onto Isabella Street.</p>
             <p id="directions">4. Continue one block and Turn Left onto Sandusky Street.</p>
             <p id="directions">5. The Andy Warhol Museum will be on your Left.</p>
          </article>
      <!-- </div>
      <div class="tile is-parent" id="itinerary-image"> -->
          <article class="tile is-child">
              <figure class="image">
                  <img src="../img/example_route.png">
              </figure>
          </article>
      </div>
  
      <div class="tile is-parent is-11" id="itinerary-item">  
          <div class="content"
          <p> 2.  </p>  
         <article class="tile is-child">
             <p class="title">Andy Warhol Museum</p>
             <p class="subtitle">Neighborhood: Northside</p>
             <div class="content"
             <p>The Andy Warhol Museum is the largest museum in North America dedicated to a single artist. The museum holds an extensive permanent collection of art and archives from the Pittsburgh-born pop art icon Andy Warhol.</p>
          </article>
      <!-- </div>
      <div class="tile is-parent" id="itinerary-image"> -->
          <article class="tile is-child">
              <figure class="image is-2by1">
                  <img src="../img/1_img.jpg">
              </figure>
          </article>
      </div>
    </div>
  
  <!-- Tile contains Starting Location info (Address, Start time, static image of house/hotel) -->
  <!-- timepicker data from SearchPage.vue -->
  
  <!-- Route (String) from API, from Starting point (homebase) to Landmark1 address -->
  
  <!-- Tiles for each landmark in rowset -->
  
  <!-- Routes (Strings) from API, from each landmark to each subsequent landmark -->
  
  <!-- Route from last landmark to End point (homebase) -->
  
  <!-- End location info (address, Approx end time, same static homebase image) -->
  
  <!-- Map from API -->
  
  </template>
  
  <script>
  import Category1Search from './Category1Search';
  import LandmarkList from './LandmarkList';
  import Datepicker from 'vuejs-datepicker';
  import Vue from 'vue';
  import Buefy from 'buefy';
  import 'buefy/dist/buefy.css';
  Vue.use(Buefy);
  
  // may need to install buefy and vuejsdatepicker
  // npm install buefy
  // npm install vuejs-datepicker --save
  
  export default {
    name: 'searchpage',
    data() {
      return {
        search: '',
        apiURL: "http://localhost:8080/AuthenticationApplication/search",
        landmarks: [],
        showModalFlag: false,
        okPressed: false,
        message: "Press 'Ok' or 'Cancel'.",
        formatAmPm: false,
        enableSeconds: false
      };
    },
    components: {
      Category1Search,
      LandmarkList,
      Datepicker
    },
    computed: {
          format() {
              return this.formatAmPm ? '12' : '24'
          }
    },
    methods: {
      showModal() {
        this.okPressed = false;
        this.showModalFlag = true;
      },
      okModal() {
        this.okPressed = true;
        this.showModalFlag = false;
      },
      cancelModal() {
        this.okPressed = false;
        this.showModalFlag = false;
      },
      handleSearch(query) {
          console.log('Filter Tasks: ', query);
          this.search = query;
      }
      
    },
    created() {
      fetch(this.apiURL)
        .then(response => {
          // console.log(response);
          return response.json();
        })
        .then(landmarks => {
          // console.log(landmarks);
          this.landmarks = landmarks;
        })
        .catch(err => console.log(err));
    }
  };
  </script>
  
  <style>
  
  body {
    /* background-image: url("../img/old-map-wallpaper-bw.jpg"); */
    background-color: #aaa;
    font-family: Palatino, "Palatino Linotype", "Palatino LT STD", "Book Antiqua", Georgia, serif;
  }
  
  div {
    /* border: 5px solid white; */
  }
  
  #itinerary-title {
    text-align: center;
    font-size: 36px;
  }
  
  #directions {
    font-size: 18px;
  }
  
  article {
    /* border: 2px solid black; */
    padding: 1.25rem;
  }
  
  #tour {
    background-color: #eee;
  }
  
  #landmark-list {
    display: flex;
    flex-wrap: wrap;
  }
  
  #itinerary-list {
    max-height: 200px;
  }
  
  h2 {
    font-size: 48px;
    text-shadow: 5px 5px 5px antiquewhite;
    margin-top: 0px;
    margin-bottom: 10px;
  }
  
  img {
    /* border-radius: 2px; */
  }
  
  p {
    font-size: 24px;
    margin: 20px;
  }
  
  .title {
    margin: 0px;
  }
  
  .subtitle {
    margin: 0px;
  }
  
  #section-row {
    margin: 10px;
  }
  </style>
  
  