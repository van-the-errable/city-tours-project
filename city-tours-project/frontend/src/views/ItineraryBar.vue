<template>
    <div class="tile is-parent" id="itinerary-list">
           <div class="tile is-child is-vertical box itinerary-tile" >
             <article class="tile is-child">
               <p>Date</p>
             </article>      
             <article class="tile is-child">
               <p>Time</p>
             </article>      
             <article class="tile is-child">
               <p>Length</p>
             </article>
           </div>
           <article class="tile is-child is-2 box itinerary-tile">
            <figure class="image is-4by3">
               <img src="http://localhost:8080/AuthenticationApplication/img/1_img.jpg" alt="placeholder">
             </figure>
             <article class="tile is-child has-text-centered">
               <p>First Landmark</p>
             </article>
           </article>     
           <article class="tile is-child is-2 box itinerary-tile">
            <figure class="image is-4by3">
               <img src="http://localhost:8080/AuthenticationApplication/img/9_img.jpg"  alt="placeholder">
             </figure>
             <article class="tile is-child has-text-centered">
               <p>Second Landmark</p>
             </article>
           </article>      
           <article class="tile is-child is-2 box itinerary-tile">
            <figure class="image is-4by3">
               <img src="http://localhost:8080/AuthenticationApplication/img/17_img.jpg"  alt="placeholder">
             </figure>
             <article class="tile is-child has-text-centered">
               <p>Third Landmark</p>
             </article>
           </article>      
           <article class="tile is-child is-2 box itinerary-tile">
            <figure class="image is-4by3">
               <img src="http://localhost:8080/AuthenticationApplication/img/25_img.jpg"  alt="placeholder">
             </figure>
             <article class="tile is-child has-text-centered">
               <p>Fourth Landmark</p>
             </article>
           </article>      
           <article class="tile is-child is-2 box itinerary-tile">
            <figure class="image is-4by3">
               <img src="http://localhost:8080/AuthenticationApplication/img/33_img.jpg"  alt="placeholder">
             </figure>
             <article class="tile is-child has-text-centered">
               <p>Fifth Landmark</p>
             </article>
           </article>
         </div> 
         </template>
   
         <style>
         .itinerary-tile {
           background-color: #eee;
           padding: 10px;
         }
         </style>