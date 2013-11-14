# Check whether LWP module is installed

if (!eval{require LWP::Simple;}) {
	exit;
}

# Retrieve the content of an URL
$url = "http://www.dia.org/art/search-collection.aspx?searchType=new&department=American+Art+before+1950&classification=Paintings&artist=&nationality=&medium=&objectNumber=&keyword=&date_from=&date_to=&action=Search+Art+at+the+DIA";

open (MYFILE, '>data.txt');
print MYFILE "{\n";
print MYFILE "\"paintings\" : [\n";

$j = 1;
while ($j <= 25) {
	$content = LWP::Simple::get($url);
	#print $content;

	@page = split('single inline',$content);
	for ($i=1; $i<scalar(@page); $i++) {
		@link = split('href="',$page[$i]);
		@link = split('"',$link[1]);
		$link[0] = "http://www.dia.org" . $link[0];
		#print $link[0];print "\n";
	
		$content = LWP::Simple::get($link[0]);
		#print $content;
	
		@artist = split('<!-- artist -->',$content);
		@artist = split('<h2>',$artist[1]);	
		@artist = split('</h2>',$artist[1]);
		@artist = split('<a href="',$artist[0]);
		@artist = split('">',$artist[1]);
		@artist = split('<',$artist[1]);
		if ($artist[0] eq "") {
			$nationality[0] = "";
			$birth = "";
			$death = "";
		}
		else {
			@nationality = split('<!-- artist -->',$content);
			@nationality = split('">',$nationality[1]);
			@nationality = split('</em>',$nationality[2]);			
			@nationality = split(',',$nationality[0]);
			$years = $nationality[1];
			
			$temp = substr($nationality[0],-1);
			if($temp eq ")") {
				$nationality[0] = 	substr($nationality[0],0,-1);
			}
			$nationality[0] = substr($nationality[0],1);
			
			$years = substr($years, 1 , -1);
			#print $years . "\n";
			$birth = substr($years, 0, 4);
			$death = substr($years, -4);
			#print $birth . " " . $death . "\n";
		}

		@image = split('<!-- large image -->',$content);
		@image = split('href="',$image[1]);
		@image = split('"',$image[1]);
		$image[0] = "http://www.dia.org" . $image[0];
		
		@title = split('<!-- title -->',$content);
		@title = split('<h1>',$title[1]);
		@title = split('</h1>',$title[1]);
		
		@date = split('class="twocol"',$content);
		@date = split('<dt>Date</dt>',$date[1]);
		@date = split('<dd>',$date[1]);
		@date = split('</dd>',$date[1]);
		
		@medium = split('class="twocol"',$content);
		@medium = split('<dt>Medium</dt>',$medium[1]);
		@medium = split('<dd>',$medium[1]);
		@medium = split('</dd>',$medium[1]);
		@medium = split('">',$medium[0]);
		@medium = split('</a>',$medium[1]);
		$medium[0] =~ s/\r\n//g;

		@dimensions = split('class="twocol"',$content);
		@dimensions = split('<dt>Dimensions</dt>',$dimensions[1]);
		@dimensions = split('<dd>',$dimensions[1]);
		@dimensions = split('</dd>',$dimensions[1]);
		$dimensions[0] =~ s/^\s+|\s+$//g;
		$dimensions[0] =~ s/\r\n//g;
		$dimensions[0] =~ s/\"/\\\"/g;

		@department = split('class="twocol"',$content);
		@department = split('<dt>Department</dt>',$department[1]);
		@department = split('<dd>',$department[1]);
		@department = split('</dd>',$department[1]);
		@department = split('">',$department[0]);
		@department = split('</a>',$department[1]);
		
		@classification = split('class="twocol"',$content);
		@classification = split('<dt>Classification</dt>',$classification[1]);
		@classification = split('<dd>',$classification[1]);
		@classification = split('</dd>',$classification[1]);
		@classification = split('">',$classification[0]);
		@classification = split('</a>',$classification[1]);

		@credit = split('class="twocol"',$content);
		@credit = split('<dt>Credit</dt>',$credit[1]);
		@credit = split('<dd>',$credit[1]);
		@credit = split('</dd>',$credit[1]);
		$credit[0] =~ s/\t/ /g;

		@accession = split('class="twocol"',$content);
		@accession = split('<dt>Accession No.</dt>',$accession[1]);
		@accession = split('<dd>',$accession[1]);
		@accession = split('</dd>',$accession[1]);

		@provenance = split('class="twocol"',$content);
		@provenance = split('<dt>Provenance</dt>',$provenance[1]);
		@provenance = split('<dd>',$provenance[1]);
		@provenance = split('</dd>',$provenance[1]);
		$provenance[0] =~ s/\r\n//g;
		$provenance[0] =~ s/\"/\\\"/g;
		$provenance[0] =~ s/\t/ /g;

		$k = 30 * ($j-1) + $i;
		print $k . "\n";
		
		#printing to file
		print MYFILE "{\n";
		print MYFILE "\"imageURL\" : \"" . $image[0] . "\",\n";		
		print MYFILE "\"title\" : \"" . $title[0] . "\",\n";		
		if ($artist[0] ne "") {
			print MYFILE "\"name\" : \"" . $artist[0] . "\",\n";		
		}
		if ($nationality[0] ne "") {
			print MYFILE "\"nationality\" : \"" . $nationality[0] . "\",\n";		
		}
		if ($birth ne "") {
			print MYFILE "\"birth\" : \"" . $birth . "\",\n";		
		}
		if ($death ne "") {
			print MYFILE "\"death\" : \"" . $death . "\",\n";		
		}
		print MYFILE "\"date\" : \"" . $date[0] . "\",\n";		
		print MYFILE "\"medium\" : \"" . $medium[0] . "\",\n";		
		if ($dimensions[0] ne "") {
			print MYFILE "\"dimensions\" : \"" . $dimensions[0] . "\",\n";		
		}
		print MYFILE "\"department\" : \"" . $department[0] . "\",\n";		
		print MYFILE "\"classification\" : \"" . $classification[0] . "\",\n";
		print MYFILE "\"credit\" : \"" . $credit[0] . "\",\n";
		if ($provenance[0] ne "") {
			print MYFILE "\"provenance\" : \"" . $provenance[0] . "\",\n";		
		}
		
		@keywords = split('class="twocol"',$content);
		@keywords = split('<dt>Keywords</dt>',$keywords[1]);
		@keywords = split('<dd>',$keywords[1]);
		@keywords = split('</dd>',$keywords[1]);
		@keywords = split('">',$keywords[0]);
		if (scalar(@keywords) > 1) {
			print MYFILE "\"keywords\" : [";
		}
		for ($x=1; $x<scalar(@keywords); $x++) {
			@word = split('</a>',$keywords[$x]);
			#print $word[0] . " ";
			print MYFILE "\"" . $word[0] . "\"";
			if ($x < scalar(@keywords)-1) {
				print MYFILE ",";
			}
		}
		if (scalar(@keywords) > 1) {
			print MYFILE "],\n";
		}
		print MYFILE "\"accession\" : \"" . $accession[0] . "\"}\n";
		# if ($k < 228 ) {
		# 	print MYFILE ",";
		# }
	}
	$j++;
	$url = "http://www.dia.org/art/search-collection.aspx?artist=&classification=Paintings&department=American+Art+before+1950&objectnumber=&keyword=&medium=&nationality=&date_from=&date_to=&the_Page=" . $j . "&all_items=";
}

print MYFILE "]\n}\n";
close (MYFILE);

sub escapeDoubleQuotes { my $s = shift; $s =~ tr{"}{\\"}; return $s };