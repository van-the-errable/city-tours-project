<template>
    <div class="tile is-ancestor">
      <div class="tile is-parent is-2 is-vertical" id="search-bar">    
         <article class="tile is-child">
           <div class="field">
              <location-search v-on:filter-tasks-location="handleSearchLocation"></location-search>
            </div>
            <div class="field example">
              <date-search v-on:filter-tasks-date="handleSearchDate"></date-search>
            </div>
            <section class="field">
               <start-time-search v-on:filter-tasks-start-time="handleSearchStartTime"></start-time-search>
            </section>
            <section class="field">
               <end-time-search v-on:filter-tasks-end-time="handleSearchEndTime"></end-time-search>
            </section>
            <neighborhood1-search v-on:filter-tasks-neighborhood1="handleSearchNeighborhood1"></neighborhood1-search>
            <!-- <neighborhood2-search v-on:filter-tasks-neighborhood2="handleSearchNeighborhood2"></neighborhood2-search> -->
            <category1-search v-on:filter-tasks-category1="handleSearchCategory1"></category1-search>
            <!-- <category2-search v-on:filter-tasks-category2="handleSearchCategory2"></category2-search> -->
            <price-search v-on:filter-tasks-price="handleSearchPrice"></price-search>
            
        </article>           
      </div>
  
      <div class="tile is-vertical" id="landmark-list">
  
        <landmark-list v-bind:landmarks="landmarks" 
                      v-bind:directions="directions" 
                      v-bind:destinationTimes="destinationTimes"
                      v-bind:searchLocation="searchLocation"
                      v-bind:searchDate="searchDate"
                      v-bind:searchStartTime="searchStartTime"
                      v-bind:searchEndTime="searchEndTime"
                      v-bind:searchCategory1="searchCategory1"
                      v-bind:searchCategory2="searchCategory2"
                      v-bind:searchNeighborhood1="searchNeighborhood1"
                      v-bind:searchNeighborhood2="searchNeighborhood2"
                      v-bind:searchPrice="searchPrice"
                      v-bind:apiURL="apiURL"></landmark-list>
  
        <itinerary-bar></itinerary-bar>      
  
      </div>
    </div>
  </template>
  
  <script>
  import LocationSearch from './LocationSearch';
  import DateSearch from './DateSearch';
  import StartTimeSearch from './StartTimeSearch';
  import EndTimeSearch from './EndTimeSearch';
  import Category1Search from './Category1Search';
  import Category2Search from './Category2Search';
  import Neighborhood1Search from './Neighborhood1Search';
  import Neighborhood2Search from './Neighborhood2Search';
  import PriceSearch from './PriceSearch';
  import LandmarkList from './LandmarkList';
  import ItineraryBar from './ItineraryBar';
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
        searchLocation: '',
        searchDate: '',
        searchStartTime: '',
        searchEndTime: '',
        searchCategory1: '',
        searchCategory2: '',
        searchNeighborhood1: '',
        searchNeighborhood2: '',
        searchPrice: '',
        apiURL: "http://localhost:8080/AuthenticationApplication/search",
        landmarks: [],
        showModalFlag: false,
        okPressed: false,
        message: "Press 'Ok' or 'Cancel'.",
        formatAmPm: false,
        enableSeconds: false,
        directions: null,
        apiMapURL: "https://cors-anywhere.herokuapp.com/https://maps.googleapis.com/maps/api/directions/json?origin=",
        apiDestination: "&destination=",
        apiKey: "&key=AIzaSyCKIb8Jt8ssOokYngFDwnqZiCOGt0J64J8",
        destinationAddress: '',
        destinationTimes: []
        // "Universal+Studios+Hollywood"
      };
    },
    components: {
      LocationSearch,
      DateSearch,
      StartTimeSearch,
      EndTimeSearch,
      Category1Search,
      Category2Search,
      Neighborhood1Search,
      Neighborhood2Search,
      PriceSearch,
      LandmarkList,
      ItineraryBar,
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
      handleSearchLocation(queryLocation) {
        // console.log('Filter TasksLocation: ', queryLocation);
        this.searchLocation = queryLocation.split(' ').join('+');
        // console.log('New address: ' + this.searchLocation);
        
        this.landmarks.forEach(landmark => {
  
          this.destinationAddress = landmark.address.split(' ').join('+') + "+" + landmark.zip;
  
          // console.log(landmark.name);
          // console.log(landmark.address.split(' ').join('+'));
  
          fetch(this.apiMapURL + this.searchLocation + this.apiDestination + this.destinationAddress + this.apiKey)
          .then(response => {
            // console.log(response);
            return response.json();
          })
          .then(directions => {
            // console.log('From promise: ', directions);
            this.directions = directions;
            // console.log(landmark.name);
            // console.log(this.destinationAddress);
            // console.log('From promise: ', this.directions.routes[0].legs[0].duration.value/60);
            // console.log('From promise: ', this.directions);
            let t = this.directions.routes[0].legs[0].duration.value/60;
            this.destinationTimes.push(t);
            // console.log(this.destinationTimes[0]);
            
          })
          .catch(err => console.log(err));
          // console.log(landmark.name);
          // console.log(this.destinationAddress);
          // console.log('Outside promise: ', this.directions);
          // console.log(this.destinationTimes[0]);
  
        });
        // for (let i = 0; i < this.destinationTimes.length; i++) {
        //     console.log(this.destinationTimes[i]);
        //     }
        // fetch(this.apiMapURL + this.searchLocation + this.apiDestination + this.destinationAddress + this.apiKey)
        //   .then(response => {
        //     // console.log(response);
        //     return response.json();
        //   })
        //   .then(directions => {
        //     // console.log('From promise: ', directions);
        //     this.directions = directions;
        //     console.log('From promise: ', this.directions);
        //   })
        //   .catch(err => console.log(err));
        //   console.log('Outside promise: ', this.directions);
      },
      handleSearchDate(queryDate) {
        console.log('Filter TasksDate: ', queryDate);
        this.searchDate = queryDate;
      },
      handleSearchStartTime(queryStartTime) {
        console.log('Filter TasksStartTime: ', queryStartTime);
        this.searchStartTime = queryStartTime;
      },
      handleSearchEndTime(queryEndTime) {
        console.log('Filter TasksEndTime: ', queryEndTime);
        this.searchEndTime = queryEndTime;
      },
      handleSearchCategory1(queryCategory1) {
          console.log('Filter TasksCategory1: ', queryCategory1);
          this.searchCategory1 = queryCategory1;
      },
      handleSearchCategory2(queryCategory2) {
          console.log('Filter TasksCategory2: ', queryCategory2);
          this.searchCategory2 = queryCategory2;
      },
      handleSearchNeighborhood1(queryNeighborhood1) {
          console.log('Filter TasksNeighborhood1: ', queryNeighborhood1);
          this.searchNeighborhood1 = queryNeighborhood1;
      },
      handleSearchNeighborhood2(queryNeighborhood2) {
          console.log('Filter TasksNeighborhood2: ', queryNeighborhood2);
          this.searchNeighborhood2 = queryNeighborhood2;
      },
      handleSearchPrice(queryPrice) {
          console.log('Filter TasksPrice: ', queryPrice);
          this.searchPrice = queryPrice;
      },
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
          this.landmarks.forEach(x=>x.showModalFlag = false);
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
  
  