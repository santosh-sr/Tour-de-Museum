
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
              <button type="submit" class="btn btn-default">Submit</button>
            </form>
           
          </div><!-- /.navbar-collapse -->
        </div>
        </nav>
        <div class="container-fluid">
        <div class="jumbotron">
            <h1 style="visibility: hidden">Tour De Museum</h1>
            <p style="visibility: hidden"><a class="btn btn-primary btn-lg">Start Now!</a></p> 
        </div>
        </div>

        <div class="container">
          <div class="row">
            <div class="col-md-4">
              <a href="#"><img class="img-responsive img-circle" src="img/rsz_lacma2.jpg"></a>
              <h3 class="text-center">Los Angeles County Museum of Art</h3>
              <p>LACMA has its roots in the Los Angeles Museum of History, Science and Art, established in 1910 in Exposition Park. In 1961, the Los Angeles County Museum of Art was established as a separate, art-focused institution. In 1965, the fledgling institution opened to the public in its new Wilshire Boulevard location, with the permanent collection in the Ahmanson Building, special exhibitions in the Hammer Building, and the 600-seat Bing Theater for public programs. 

              Over several decades, the campus and the collection have grown considerably. The Anderson Building (renamed the Art of the Americas building in 2007) opened in 1986 to house modern and contemporary art. In 1988, Bruce Goff's innovative Pavilion for Japanese Art opened at the east end of campus. In 1994, the museum acquired the May Company department store building at the corner of Wilshire and Fairfax, now known as LACMA West.</p>
            </div>
            <div class="col-md-4">
              <a href="#"><img class="img-responsive img-circle" src="img/rsz_detroit2.jpg"></a>
              <h3 class="text-center">Detroit Institute of Arts</h3>
              <p>The DIA has been a beacon of culture for the Detroit area for well over a century. Founded in 1885, the museum was originally located on Jefferson Avenue, but, due to its rapidly expanding collection, moved to a larger site on Woodward Avenue in 1927. The new Beaux-Arts building, designed by Paul Cret, was immediately referred to as the "temple of art." Two wings were added in the 1960s and 1970s, and a major renovation and expansion that began in 1999 was completed in 2007.</p>
            </div>
            <div class="col-md-4">
              <a href="index.php?museum=artic"><img class="img-responsive img-circle" src="img/artic.jpg"></a>
              <h3 class="text-center">The Art Institute of Chicago</h3>
              <p>The Art Institute of Chicago (AIC) is an encyclopedic art museum located in Chicago's Grant Park. It features a collection of Impressionist and Post-Impressionist art in its permanent collection. Its holdings also include American art, Old Masters, European and American decorative arts, Asian art, modern and contemporary art, and architecture and industrial and graphic design. In addition, it houses the Ryerson &amp; Burnham Libraries.</p>
            </div>
          </div>
        </div>
        <footer>
          <div class="container">
          <div class="row">
            <div class="col-md-4">&copy; 2013</div>
           <!--  <div class="col-md-4">
              <ul class="nav nav-pills">
                <li class="active"><a href="#">About Us</a></li>
                <li><a href="#">Support 24x7</a></li>
                <li><a href="#">Privacy Policy</a></li>
              </ul>
            </div>
            <div class="col-md-4">
              <h3 class="text-right">Vacation Rentals</h3>
            </div>
          </div> -->
          </div>
        </footer>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
     <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
  </body>
</html>