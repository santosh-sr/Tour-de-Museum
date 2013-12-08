<!doctype html>

<html>
<head>
  <meta charset="utf-8">
  <title>Tour De Museum SlideShow</title>
  
  <!-- SlidesJS Required (if responsive): Sets the page width to the device width. -->
  <meta name="viewport" content="width=device-width">
  <!-- End SlidesJS Required -->

  <!-- CSS for slidesjs.com example -->
  <!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap-theme.min.css">
  <link rel="stylesheet" href="css/example.css">
  <link rel="stylesheet" href="css/font-awesome.min.css">

  <!-- End CSS for slidesjs.com example -->

  <!-- SlidesJS Optional: If you'd like to use this design -->
  <style>
    body {
      -webkit-font-smoothing: antialiased;
      font: normal 15px/1.5 "Helvetica Neue", Helvetica, Arial, sans-serif;
      color: #232525;
      padding-top:70px;
    }

    #rounded{ /* the outermost div element */
      width:800px;
      margin:20px auto; /*center it on the page*/
   }

    #slides {
      display: none
    }

    #slides .slidesjs-navigation {
      margin-top:3px;
    }

    #slides .slidesjs-previous {
      margin-right: 5px;
      float: left;
    }

    #slides .slidesjs-next {
      margin-right: 5px;
      float: left;
    }

    .slidesjs-pagination {
      margin: 6px 0 0;
      float: right;
      list-style: none;
    }

    .slidesjs-pagination li {
      float: left;
      margin: 0 1px;
    }

    .slidesjs-pagination li a {
      display: block;
      width: 13px;
      height: 0;
      padding-top: 13px;
      background-image: url(img/pagination.png);
      background-position: 0 0;
      float: left;
      overflow: hidden;
    }

    .slidesjs-pagination li a.active,
    .slidesjs-pagination li a:hover.active {
      background-position: 0 -13px
    }

    .slidesjs-pagination li a:hover {
      background-position: 0 -26px
    }

    #slides a:link,
    #slides a:visited {
      color: #333
    }

    #slides a:hover,
    #slides a:active {
      color: #9e2020
    }

    .navbar {
      overflow: hidden
    }
  </style>
  <!-- End SlidesJS Optional-->

  <!-- SlidesJS Required: These styles are required if you'd like a responsive slideshow -->
  <style>
    #slides {
      display: none
    }

    .container {
      margin: 0 auto
    }

    /* For tablets & smart phones */
    @media (max-width: 767px) {
      body {
        padding-left: 20px;
        padding-right: 20px;
      }
      .container {
        width: auto
      }
    }

    /* For smartphones */
    @media (max-width: 480px) {
      .container {
        width: auto
      }
    }

    /* For smaller displays like laptops */
    @media (min-width: 768px) and (max-width: 979px) {
      .container {
        width: 724px
      }
    }

    /* For larger displays */
    @media (min-width: 1200px) {
      .container {
        width: 1170px
      }
    }
  </style>
  <!-- SlidesJS Required: -->
</head>
<body>
  <nav class="navbar-wrapper navbar-inverse navbar-fixed-top" role="navigation">
  <div class="navbar-header ">

    <a class="navbar-brand" href="#">Tour de Museum</a>
  </div>
    <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
              <li><a href="home.php"><span class="glyphicon glyphicon-home"></span> Home</a></li>
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span> About Us<b class="caret"></b></a>
                <ul class="dropdown-menu">
                  <li><a href="#">Santosh Ravi</a></li>
                  <li><a href="#">Ankit Kalwar</a></li>
                </ul>
              </li>
            </ul>

    <div class="navbar-form navbar-right form-inline" role="search">
      <div class="form-group" >
      <select id="menu" name="menu" class="form-control" >
          <option value="0" selected>--Please Select--</option>
          <option value="1">Title</option>
          <option value="2">Artist</option>
          <option value="3">Painted Year</option>
          <option value="4">Medium</option>
        </select>
      </div>
      <div class="form-group" style="margin-left: 10px; margin-right: 10px;">
        <input id="searchtext" type="text" class="form-control" placeholder="Search">
      </div>
    <!-- <button type="submit" style="margin-top: -1px;" onClick="textSearch()" class="btn btn-default">Submit</button> -->
    <button type="submit" style="margin-top: -1px;" onClick="goto()" class="btn btn-default">Submit</button>
    </div>
</div>
  </nav>

  <div id="rounded" style="width:100%">
  
   
  <div class="row">
  <div class='col-md-8'>

  <!-- SlidesJS Required: Start Slides -->
  <!-- The container is used to define the width of the slideshow -->
  <!-- border-width: 2px; border-style: solid; border-color: black;  -->
  <div class="container" style="width:inherit">
    <div id="slides">
     <!--  <img src="img/example-slide-1.jpg" alt="Photo by: Missy S Link: http://www.flickr.com/photos/listenmissy/5087404401/">
      <img src="img/example-slide-2.jpg" alt="Photo by: Daniel Parks Link: http://www.flickr.com/photos/parksdh/5227623068/">
      <img src="img/example-slide-3.jpg" alt="Photo by: Mike Ranweiler Link: http://www.flickr.com/photos/27874907@N04/4833059991/">
      <img src="img/example-slide-4.jpg" alt="Photo by: Stuart SeegerLink: http://www.flickr.com/photos/stuseeger/97577796/"> -->
      <a href="#" class="slidesjs-previous slidesjs-navigation"><i class="icon-chevron-left icon-large"></i></a>
      <a href="#" class="slidesjs-next slidesjs-navigation"><i class="icon-chevron-right icon-large"></i></a>
    </div>
   
    </div>
    </div>
    <div class='col-md-4' id='description' style="top: 20px"></div>
  </div>


 </div>
  <!-- End SlidesJS Required: Start Slides -->

  <!-- SlidesJS Required: Link to jQuery -->
  <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
  <!-- End SlidesJS Required -->

  <!-- SlidesJS Required: Link to jquery.slides.js -->
  <script src="js/jquery.slides.min.js"></script>
  <!-- End SlidesJS Required -->

  <!-- SlidesJS Required: Initialize SlidesJS with a jQuery doc ready -->
  <script>


var museumName= "<?php echo $_GET['museum'] ?>";
var clusterType= "<?php if(isset($_GET['clusterType'])){ echo $_GET['clusterType']; }?>";
var clusterVal= "<?php if(isset($_GET['clusterVal'])){ echo $_GET['clusterVal']; }?>";


//fetch data for museum
fetchdata(museumName, clusterType, clusterVal);

function fetchdata(museum, clusterType, clusterVal){
  if(typeof clusterType != 'undefined' && clusterType.length > 0){
    if(clusterType == 'Artist'){
      query = "select * from painting p left join classifier c on p.painting_id = c.painting_id where p.museum_name = '" + museum + "' and p.artist_name like '%" + clusterVal + "%'";
      //query = "select distinct p.* from artist a, painting p where a.painting_id = p.painting_id and (a.artist_name = '" + clusterVal + "' or a.org_artist_name = '" + clusterVal+"')";
    }else if(clusterType == 'Painted Year'){
      query = "select * from painting p left join classifier c on p.painting_id = c.painting_id where p.museum_name = '" + museum + "' and p.date = '" + clusterVal + "'";
      //query = "select distinct p.* from painted_year a, painting p where a.painting_id = p.painting_id and (a.year = '" + clusterVal + "' or a.org_year = '" + clusterVal + "')";
    }else if(clusterType == 'Medium'){
      query = "select * from painting p left join classifier c on p.painting_id = c.painting_id where p.museum_name = '" + museum + "' and p.medium like '%" + clusterVal + "%'";
      //query = "(select distinct p.* from medium a, painting p where a.painting_id = p.painting_id and (a.medium = '" + clusterVal + "' or a.org_medium = '" + clusterVal + "') and p.museum_name = 'detroit' limit 10) union (select distinct p.* from medium a, painting p where a.painting_id = p.painting_id and (a.medium = '" + clusterVal + "' or a.org_medium = '" + clusterVal + "') and p.museum_name = 'lacma' limit 10) union (select distinct p.* from medium a, painting p where a.painting_id = p.painting_id and (a.medium = '" + clusterVal + "' or a.org_medium = '" + clusterVal + "') and p.museum_name = 'artic' limit 10)";
    }else if(clusterType == 'Title'){
      query = "select * from painting p left join classifier c on p.painting_id = c.painting_id where p.museum_name = '" + museum + "' and p.title like '%" + clusterVal + "%'";
      //query = "select distinct p.* from classifier a, painting p where a.painting_id = p.painting_id and a.classifier_name = '" + clusterVal + "'";
    }
  }else{
    query = "select * from painting p left join classifier c on p.painting_id = c.painting_id where p.museum_name = '" + museum + "'";  

  }
   
   $.ajax({

                url: "mysql.php?query="+ encodeURI(query),
                type: 'GET',
                dataType: 'text',


                success: function(data){
            
                    jsonData = jQuery.parseJSON(data);
                    console.log(jsonData);

                    for(var data in jsonData){
                      var elem = document.createElement('img');
                      elem.src = jsonData[data].imageURL;
                      document.getElementById("slides").appendChild(elem);
                    }
                   
                    $(function() {
                      $('#slides').slidesjs({
                        width: 565,
                        height: 700,
                        myjson: jsonData,
                        navigation: false
                      });
                    });
                },

                error: function(error, jqXHR)
                {
                    alert(jqXHR);
                }

                
            });

}

  function textSearch(){
    var clusterType = $("#menu").find('option:selected').text();
    var searchText = document.getElementById("searchtext").value;

    
  }

  function goto()
{
  window.location.assign("/td/index.php?museum=<?php echo $_GET['museum'] ?>&clusterType="+$("#menu").find('option:selected').text()+"&clusterVal="+$('#searchtext').val());
}

  </script>
  <!-- End SlidesJS Required -->
</body>
</html>