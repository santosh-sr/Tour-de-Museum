
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style type="text/css">

.node {
  stroke: #fff;
  stroke-width: 1.5px;
}

.link {
  stroke: #999;
  stroke-opacity: .6;
}
.modal-vertical-centered {
transform: translate(0, 10%) !important;
-ms-transform: translate(0, 10%) !important; /* IE 9 */
-webkit-transform: translate(0, 10%) !important; /* Safari and Chrome */
}

#img-modal{
    display: inline-block;
    vertical-align: top;
}
#description-modal{
    display: inline-block;
    max-height: 350px;
    margin-left:   15px;
    width:210px;
    overflow-y:auto;
    overflow-x:visible ;
}
.content{
  overflow:auto
}
body
{
    padding-top: 70px;
}

    </style>

     <link href="css/customstyle.css" rel="stylesheet" media="screen">d
  </head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
  <div class="navbar-header ">
    <a class="navbar-brand" href="#">Tour de Museum</a>
  </div>
  <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
              <li ><a href="home.php"><span class="glyphicon glyphicon-home"></span> Home</a></li>
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span> About Us<b class="caret"></b></a>
                <ul class="dropdown-menu">
                  <li><a href="#">Santosh Ravi</a></li>
                  <li><a href="#">Ankit Kalwar</a></li>
                </ul>
              </li>
            </ul>
    </div>

    <form class="navbar-form navbar-right" role="search">
  <div class="form-group " style="margin-right: 10px;">
    <input type="text" class="form-control" placeholder="Search">
  </div>
  <button type="submit" class="btn btn-default">Submit</button>
</form>
  </nav>
<div class="container" style="width:100%;"> 
   <!--  <pre id="hero" style="overflow:scroll">

    </pre> -->
     <pre id="hero" style="width:100%">

    </pre>
    <!--<div style="width:100%; height:100%;" id='render'></div> -->

</div> 

<!-- Button trigger modal -->
<div  id="mymodal" class="modal fade modal-vertical-centered">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="modal-title">Modal title</h4>
      </div>
      <div class="modal-body" id="modal-body">
        <p>One fine body&hellip;</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script type="text/javascript" src="http://d3js.org/d3.v2.min.js?2.9.6"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>

<script type="text/template" id="modal-template">
<div id="img-modal">
    <img src=<%= data.image%> width="300px" height="300px"></img>
</div>
<div id="description-modal" >
   <b> Painted Year: </b> <%= data.date%> <br/>
   <b> Medium: </b> <%= data.medium%> <br/>
   <b> Artist Name: </b> <%= data.artist_name%> <br/>
   <b> Birth Year: </b> <%= data.birth_year%> <br/>
   <b> Death Year: </b> <%= data.death_year%> <br/>  
   <b> Nationality: </b> <%= data.nationality%> <br/>
   <% if (data.classification.length > 0) { %>
     <b> Classification: </b> <%= data.classification%> <br/>
   <% } %>
   <% if (data.dimensions.length > 0) { %>
     <b> Dimensions: </b> <%= data.dimensions%> <br/>
   <% } %>
   <% if (data.credit.length > 0) { %>
     <b> Credit: </b> <%= data.credit%> <br/>
   <% } %>
   
   <% if (data.provenance.length > 0) { %>
     <b> Provenance: </b> <%= data.provenance%> <br/>
   <% } %>
   <% if (data.description.length > 0) { %>
     <b> Description: </b> <%= data.description%> <br/>
   <% } %>
   
</div>
</script>

<script type="text/javascript">

var clusterName= "<?php if(isset($_GET['artist'])){
    $clusterName = $_GET['artist'];
    }elseif(isset($_GET['year'])){
    $clusterName = $_GET['year'];
    }elseif(isset($_GET['medium'])){
    $clusterName = $_GET['medium'];
    }elseif(isset($_GET['classifier'])){
    $clusterName = $_GET['classifier'];
    }

    echo $clusterName; ?>";
var query= "<?php if(isset($_GET['artist'])){
    $artist = $_GET['artist'];
    $query = "select distinct p.* from artist a, painting p where a.painting_id = p.painting_id and (a.artist_name = '$artist' or a.org_artist_name = '$artist')";
    }elseif(isset($_GET['year'])){
    $year = $_GET['year'];
    $query = "select distinct p.* from painted_year a, painting p where a.painting_id = p.painting_id and (a.year = '$year' or a.org_year = '$year')";
    }elseif(isset($_GET['medium'])){
    $medium = $_GET['medium'];
    $query = "(select distinct p.* from medium a, painting p where a.painting_id = p.painting_id and (a.medium = '$medium' or a.org_medium = '$medium') and p.museum_name = 'detroit' limit 10) union (select distinct p.* from medium a, painting p where a.painting_id = p.painting_id and (a.medium = '$medium' or a.org_medium = '$medium') and p.museum_name = 'lacma' limit 10) union (select distinct p.* from medium a, painting p where a.painting_id = p.painting_id and (a.medium = '$medium' or a.org_medium = '$medium') and p.museum_name = 'artic' limit 10)";
    }elseif(isset($_GET['classifier'])){
    $classifier = $_GET['classifier'];
    $query = "select distinct p.* from classifier a, painting p where a.painting_id = p.painting_id and a.classifier_name = '$classifier'";
    }

    echo $query;
?>";

$.ajax({

                url: "mysql.php?query="+query,
                type: 'GET',
                dataType: 'text',


                success: function(data){
                    jsonData = jQuery.parseJSON(data);
                    graph = buildGraph(jsonData, clusterName);
                    drawGraph(graph);
                },

                error: function(error, jqXHR)
                {
                    alert(jqXHR);
                }

                
            });


var width = 1100,
    height = 650;

var color = d3.scale.category20();


var force = d3.layout.force()

    .charge(-500)
    .linkDistance(150)
    .size([width, height]);

var svg = d3.select("#hero").append("svg")
    .attr("width", '100%')
    .attr("height", '150%');
    
    // .style("overflow", "auto");


var drawGraph = function(graph) {
  force
      .nodes(graph.nodes)
      .links(graph.links)
      .start();

  var link = svg.selectAll(".link")
      .data(graph.links)
    .enter().append("line")
      .attr("class", "link")
      .style("stroke-width", function(d) { return Math.sqrt(d.value); });

  var gnodes = svg.selectAll('g.gnode')
     .data(graph.nodes)
     .enter()
     .append('g')
     //.classed('gnode', true);
     .call(force.drag)
      .on("click", click);

      gnodes.append("image")
      .attr("xlink:href", function(d){ 
        if(d.group == 1){
          return "http://www.patricklewis.net/games/spinpuz/Red-circle.png";
        }else if(d.group == 2){
          return "http://openclipart.org/image/250px/svg_to_png/3201/nlyl_blue_circle.png";
        }else{
          return d.image;
        }
      })
      //.attr("class", "img-circle")
       .attr("width", 50)
      .attr("height", 50)
      .attr("x", function(d){
        if(d.group == 1){
          return '-22';
        }else if(d.group == 2){
          return '-22';
        }
      })
      .attr("y", function(d){
        if(d.group == 1){
          return '-22';
        }else if(d.group == 2){
          return '-22';
        }
      });
      ;
    
  var labels = gnodes.append("text")
      .text(function(d) { return d.name; });

  
  force.on("tick", function() {
    link.attr("x1", function(d) { return d.source.x; })
        .attr("y1", function(d) { return d.source.y; })
        .attr("x2", function(d) { return d.target.x; })
        .attr("y2", function(d) { return d.target.y; });


    gnodes.attr("transform", function(d) { 
        return 'translate(' + [d.x, d.y] + ')'; 
    }); 
    
      
  });
};

// Toggle children on click.
function click(d) {
    $('#modal-title').html(d.name);
    var template = _.template($("#modal-template").html(), {data: d});

    $('#modal-body').html(template);
    $('#mymodal').modal({});
}

function buildGraph(jsonData, clusterName){

    var museumArray = {"lacma":3, "detroit": 4, "artic": 5};
    var graph = {"nodes":[], "links":[{"source":1,"target":0,"value":10},
    {"source":2,"target":0,"value":10},
    {"source":3,"target":0,"value":10}
    ]};

    graph.nodes.push({"name": clusterName, "group":1, "image":null, "weight":500});
    graph.nodes.push({"name":"lacma".toUpperCase(), "group":2, "image":null, "weight":50});
    graph.nodes.push({"name":"detroit".toUpperCase(), "group":2, "image":null, "weight":50});
    graph.nodes.push({"name":"artic".toUpperCase(), "group":2, "image":null, "weight":50});

    count = 3;
    for(var data in jsonData){
        var obj = jsonData[data];
       graph.nodes.push({"name": obj.title, "group":museumArray[obj.museum_name], "image":obj.imageURL, "painting_id": obj.painting_id, "birth_year": obj.birth_year, "death_year": obj.death_year, "nationality": obj.nationality, "accession_id": obj.accession_id, "dimensions": obj.dimensions, "credit": obj.credit, "date": obj.date, "medium": obj.medium, "provenance": obj.provenance, "description": obj.description, "department": obj.department, "museum_name": obj.museum_name, "artist_name": obj.artist_name, "classification": obj.classification});
       graph.links.push({"source": ++count, "target":(museumArray[obj.museum_name] - 2), "value":10});
    }

    return graph;
}


</script>
</body>
</html>
