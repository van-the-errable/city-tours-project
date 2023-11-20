<template>
    <div class="tile landmark-list" id="landmark-list">
      <div
        class="tile is-parent is-vertical is-3"
        v-for="landmark in filteredLandmarks"
        v-bind:key="landmark.id"
      >
        <article class="tile is-child box has-text-centered" id="landmark-tile">
          <figure class="image is-4by3">
            <img
              :src="'http://localhost:8080/AuthenticationApplication/img/' + landmark.id + '_img.jpg'"
            />
          </figure>
  
          <p id="landmark-tile-name">{{landmark.name}}</p>
  
          <section class="section" id="landmark-tile-buttons">
            <div class="container">
              <button
                class="button input"
                id="info-button"
                @click.prevent="showModal(landmark)"
              >More Information</button>
              <button class="button input" @click="addToItinerary(landmark)">Add to Itinerary</button>
            </div>
  
            <div class="modal" :class="{'is-active': isModal(landmark.id)}">
              <div class="modal-background"></div>
              <div class="modal-card">
                <header class="modal-card-head">
                  <p class="modal-card-title">{{landmark.name}}</p>
                  <button class="delete" aria-label="close" @click="cancelModal(landmark)">></button>
                </header>
                <section class="modal-card-body">
                  <img
                    :src="'http://localhost:8080/AuthenticationApplication/img/' + landmark.id + '_img.jpg'"
                  />
                  <span>{{landmark.description}}</span>
                </section>
                <footer class="modal-card-foot">
                  <button class="button is-success" @click="okModal(landmark)">Ok</button>
                  <button class="button" @click="cancelModal(landmark)">Cancel</button>
                </footer>
              </div>
            </div>
          </section>
        </article>
      </div>
    </div>
  </template>
  
  <script>
  import Vue from "vue";
  import Buefy from "buefy";
  import "buefy/dist/buefy.css";
  Vue.use(Buefy);
  
  export default {
    data() {
      return {
        message: "Press 'Ok' or 'Cancel'.",
        modalLandmark: -1,
        apiKey: "AIzaSyCKIb8Jt8ssOokYngFDwnqZiCOGt0J64J8",
        apiMapURL: "https://maps.googleapis.com/maps/api/directions/json?",
        startLocation: "900+Pennsylvania+Avenue+Pittsburgh",
        endLocation: "Universal+Studios+Hollywood"
      };
    },
    props: {
      apiURL: String,
      searchLocation: String,
      searchDate: String,
      searchStartTime: String,
      searchEndTime: String,
      searchCategory1: String,
      searchCategory2: String,
      searchNeighborhood1: String,
      searchNeighborhood2: String,
      searchPrice: String,
      landmarks: Array,
      directions: Object,
      destinationTimes: Array
    },
    computed: {
      filteredLandmarks() {
        const filterCategory1 = this.searchCategory1;
        const filterCategory2 = this.searchCategory2;
        const filterNeighborhood1 = this.searchNeighborhood1;
        const filterNeighborhood2 = this.searchNeighborhood2;
        const filterPrice = this.searchPrice;
        const filterLocation = this.searchLocation;
        const filterDate = this.searchDate;
        const filterStartTime = this.searchStartTime;
        const filterEndTime = this.searchEndTime;
        let filteredLandmarksList = this.landmarks;
        let startTime = 0;
        let endTime = 0;
        let tourLength = 0;
  
        let searchParameters = [];
        searchParameters[0] = filterCategory1;
        searchParameters[1] = filterCategory2;
        searchParameters[2] = filterNeighborhood1;
        searchParameters[3] = filterNeighborhood2;
        searchParameters[4] = filterPrice;
        searchParameters[5] = filterLocation;
        searchParameters[6] = filterDate;
        searchParameters[7] = filterStartTime;
        searchParameters[8] = filterEndTime;
  
        // console.log('Array[5]: ' + searchParameters[5]);
        // console.log('Array[6]: ' + searchParameters[6]);
        // console.log('Array[7]: ' + searchParameters[7]);
        // console.log('Array[8]: ' + searchParameters[8]);
        // if (this.directions !== null) {
        //   for (let i = 0; i < this.destinationTimes.length; i++) {
        //     console.log(this.destinationTimes[i]);
        //     }
        // console.log('From component: ', this.directions.routes[0].legs[0].duration.value/60);
        // }
        if (filterDate !== "") {
          let dateArray = filterDate.split(" ");
  
          switch (dateArray[1]) {
            case "Jan":
              dateArray[1] = "01";
              break;
            case "Feb":
              dateArray[1] = "02";
              break;
            case "Mar":
              dateArray[1] = "03";
              break;
            case "Apr":
              dateArray[1] = "04";
              break;
            case "May":
              dateArray[1] = "05";
              break;
            case "Jun":
              dateArray[1] = "06";
              break;
            case "Jul":
              dateArray[1] = "07";
              break;
            case "Aug":
              dateArray[1] = "08";
              break;
            case "Sep":
              dateArray[1] = "08";
              break;
            case "Oct":
              dateArray[1] = "10";
              break;
            case "Nov":
              dateArray[1] = "11";
              break;
            case "Dec":
              dateArray[1] = "12";
              break;
          }
  
          let dateString = dateArray[2] + "-" + dateArray[1] + "-" + dateArray[0];
          let dayOfWeek = new Date(dateString).getDay();
          let isWeekend = dayOfWeek === 5 || dayOfWeek === 6 ? true : false;
  
          // console.log(this.landmarks[0].weekdayOpen);
  
          if (filterStartTime !== "" && filterEndTime !== "") {
            // console.log(filterStartTime + "   " + filterEndTime);
  
            let startString = filterStartTime.split(":");
            let endString = filterEndTime.split(":");
  
            startTime = parseInt(startString[0]) * 60 + parseInt(startString[1]);
            endTime = parseInt(endString[0]) * 60 + parseInt(endString[1]);
  
            if (!startString[1].includes("AM")) {
              if (parseInt(startString[0]) === 12) {
                startTime = 720 + parseInt(startString[1]);
              } else {
                startTime += 720;
              }
            }
            if (!endString[1].includes("AM")) {
              if (parseInt(endString[0]) === 12) {
                endTime = 720 + parseInt(endString[1]);
              } else {
                endTime += 720;
              }
            }
  
            tourLength = endTime - startTime;
  
            console.log("start: " + startTime);
            console.log("end: " + endTime);
            console.log("length: " + tourLength);
            console.log(isWeekend);
  
            if (!isWeekend) {
              filteredLandmarksList = filteredLandmarksList.filter(
                x => x.weekdayOpen <= startTime && x.weekdayClose >= startTime && x.weekdayOpen <= endTime
              );
            } else {
              filteredLandmarksList = filteredLandmarksList.filter(
                x => x.weekendOpen <= startTime && x.weekendClose >= startTime && x.weekendOpen <= endTime
              );
            }
          }
  
          //   if (this.directions !== null) {
  
          //   for (let i = 0; i < this.landmarks.length; i++) {
          //     console.log(this.destinationTimes[i] + this.landmarks[i].minVisitLength * 60);
          //     }
  
          //   filteredLandmarksList = filteredLandmarksList.filter(
  
          //     x => (this.destinationTimes[x.id - 1] + (x.minVisitLength * 60)) >= tourLength
  
          //   );
          // }
        }
  
        if (searchParameters[0] != "") {
          filteredLandmarksList = filteredLandmarksList.filter(
            x => x.category === searchParameters[0]
          );
        }
        if (searchParameters[2] != "") {
          filteredLandmarksList = filteredLandmarksList.filter(
            x => x.neighborhood === searchParameters[2]
          );
        }
        if (searchParameters[4] != "") {
          let searchInt = Number(searchParameters[4]);
          filteredLandmarksList = filteredLandmarksList.filter(
            x => x.price === searchInt
          );
        }
  
        return filteredLandmarksList;
      }
    },
  
    methods: {
      isModal(landmarkId) {
        return landmarkId === this.modalLandmark;
      },
      showModal(landmark) {
        console.log(landmark.id);
        this.modalLandmark = landmark.id;
      },
      okModal() {
        this.modalLandmark = -1;
      },
      cancelModal() {
        this.modalLandmark = -1;
      },
      addToItinerary(landmark) {
        fetch("http://localhost:8081/AuthenticationApplication/search", {
          method: "post",
          body: JSON.stringify() // this will return values from the key-value pairs of the JSON Object
        })
          .then(response => {
            return response.json();
          })
          .then(data => {
            console.log(data);
            buildRow(data);
          })
          .catch(error => {
            console.log(error);
          });
      },
      buildRow(data) {
        const ul = document.createElement("ul");
        data.forEach(data => {
          const li = document.createElement("li");
          li.innerText = data.name;
          const img = document.createElement("img");
        });
        li.appendChild(img);
        ul.appendChild(li);
      }
    }
  };
  
  // Add to Cart from Space Store Geek
  // <div class="purchase-form">
  // 	<c:url var="formAction" value="/spaceStore/productDetail" />
  // 	<form method="POST" action="${formAction}">
  // 		<div>
  //     	<input class="addToCartButton" type="submit" value="Add to Cart" />
  // 		</div>
  // 		<div class="formInputGroup">
  // 			<label for="quantity">Quantity:</label>
  // 			<input type="number" min="1" name="quantity" id="quantity" default="1" />
  // 			<input type="hidden" name="id" value="${ param.id }"/>
  // 		</div>
  // 	</form>
  // </div>
  </script>
  
  <style>
  .landmark-list {
    margin-right: 20px;
  }
  
  #landmark-tile-buttons {
    padding: 0.5rem;
  }
  
  #landmark-tile-name {
    font-size: 18px;
    min-height: 70px;
    padding: 10px;
  }
  
  #landmark-tile {
    background-color: #eee;
  }
  
  #info-button {
    margin-bottom: 0.5rem;
  }
  </style>
  
  
  
  