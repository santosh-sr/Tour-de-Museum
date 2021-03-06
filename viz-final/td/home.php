
<!DOCTYPE html>
<html>
  <head>
    <title>Tour De Museum</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="css/customstyle.css" rel="stylesheet" media="screen">
  </head>
  <body>
          <nav class="navbar-wrapper navbar-inverse navbar-fixed-top" role="navigation">
          <div class="container">
          <!-- Brand and toggle get grouped for better mobile display -->
          <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Tour De Museum</a>
          </div>

          <!-- Collect the nav links, forms, and other content for toggling -->
          <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
              <li class="active"><a href="#"><span class="glyphicon glyphicon-home"></span> Home</a></li>
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span> About Us<b class="caret"></b></a>
                <ul class="dropdown-menu">
                  <li><a href="#">Santosh Ravi</a></li>
                  <li><a href="#">Ankit Kalwar</a></li>
                </ul>
              </li>
            </ul>
            <form class="navbar-form navbar-right" role="search">
              <div class="form-group">
                <input type="text" class="form-control" placeholder="Search">
              </div>
              <button type="submit"  style="margin-right: 10px;" class="btn btn-default">Submit</button>
            </form>
           
          </div><!-- /.navbar-collapse -->
        </div>
        </nav>
        <div class="container-fluid">
        <div class="jumbotron">  <!-- style="visibility: hidden"-->
            <h3 style="visibility: hidden"> <small> When in a museum, walk slowly but keep walking. <small/></h3> 
            
            <p style="visibility: hidden"><a class="btn btn-primary btn-lg">Start Now!</a></p> 
        </div>
        </div>

        <div class="container">
         <!--  <div>
          <blockquote class="pull-right">
              <p>When in a museum, walk slowly but keep walking.</p>
              <small><cite title="Source Title">Gertrude Stein</cite></small>
            </blockquote>
        </div> -->
        
          <div class="row">
            <div class="col-md-4">
              <a href="index.php?museum=lacma"><img class="img-responsive img-circle" src="img/rsz_lacma2.jpg"></a>
              <h3 class="text-center">Los Angeles County Museum of Art</h3>
              <p align="justify">LACMA has its roots in the Los Angeles Museum of History, Science and Art, established in 1910 in Exposition Park. In 1961, the Los Angeles County Museum of Art was established as a separate, art-focused institution. In 1965, the fledgling institution opened to the public in its new Wilshire Boulevard location, with the permanent collection in the Ahmanson Building, special exhibitions in the Hammer Building, and the 600-seat Bing Theater for public programs.<br>

              <br>Over several decades, the campus and the collection have grown considerably. The Anderson Building (renamed the Art of the Americas building in 2007) opened in 1986 to house modern and contemporary art. In 1988, Bruce Goff's innovative Pavilion for Japanese Art opened at the east end of campus. In 1994, the museum acquired the May Company department store building at the corner of Wilshire and Fairfax, now known as LACMA West.</p>
            </div>
            <div class="col-md-4">
              <a href="index.php?museum=detroit"><img class="img-responsive img-circle" src="img/rsz_detroit2.jpg"></a>
              <h3 class="text-center">Detroit Institute of Arts</h3>
              <p align="justify"><br>The DIA has been a beacon of culture for the Detroit area for well over a century. Founded in 1885, the museum was originally located on Jefferson Avenue, but, due to its rapidly expanding collection, moved to a larger site on Woodward Avenue in 1927. The new Beaux-Arts building, designed by Paul Cret, was immediately referred to as the "temple of art." Two wings were added in the 1960s and 1970s, and a major renovation and expansion that began in 1999 was completed in 2007.<br>

              <br>The DIA's collection is among the top six in the United States, comprising a multicultural and multinational survey of human creativity from prehistory through the 21st century. The foundation was laid by William Valentiner, a scholar and art historian from Berlin, who was director from 1924 to 1945 . His extensive contacts in Europe, along with support from generous patrons, enabled him to acquire many important works that established the framework of today's collections. Among the notable acquisitions during his tenure are Mexican artist Diego Rivera's Detroit Industry fresco cycle, which Rivera considered his most successful work, and Vincent van Gogh's Self Portrait, the first Van Gogh painting to enter a U.S. museum collection.
              </p>
            </div>
            <div class="col-md-4">
              <a href="index.php?museum=artic"><img class="img-responsive img-circle" src="img/rsz_artic2.jpg"></a>
              <h3 class="text-center">The Art Institute of Chicago</h3>
              <p align="justify"><br>The Art Institute of Chicago was founded as both a museum and school for the fine arts in 1879, a critical era in the history of Chicago as civic energies were devoted to rebuilding the metropolis that had been destroyed by the Great Fire of 1871. Its first collections consisting primarily of plaster casts, the Art Institute found its permanent home in 1893, when it moved into a building, constructed jointly with the city of Chicago for the World's Columbian Exposition, at the intersection of Michigan Avenue and Adams Street. <br> <br>That building, its entry flanked by the two famous bronze lions, remains the "front door" of the museum even today. In keeping with the academic origins of the institution, a research library was constructed in 1901; eight major expansions for gallery and administrative space have followed, with the latest being the Modern Wing, which opened in 2009.The permanent collection has grown from plaster casts to nearly 300,000 works of art in fields ranging from Chinese bronzes to contemporary design and from textiles to installation art. Together, the School of the Art Institute of Chicago and the museum of the Art Institute of Chicago are now internationally recognized as two of the leading fine-arts institutions in the United States. </p>
            </div>
          </div>
        </div>
        <!-- <footer>
          <div class="container">
          <div class="row">
            <div class="col-md-4">&copy; 2013</div>
            <div class="col-md-4">
              <ul class="nav nav-pills">
                <li class="active"><a href="#">About Us</a></li>
                <li><a href="#">Support 24x7</a></li>
                <li><a href="#">Privacy Policy</a></li>
              </ul>
            </div>
            <div class="col-md-4">
              <h3 class="text-right">Vacation Rentals</h3>
            </div>
          </div>
          </div>
        </footer> -->

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
     <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
  </body>
</html>