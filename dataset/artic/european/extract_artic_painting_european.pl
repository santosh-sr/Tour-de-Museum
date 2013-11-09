# Check whether LWP module is installed

if (!eval{require LWP::Simple;}) {
	exit;
}

# Retrieve the content of an URL
$url = "http://www.artic.edu/aic/collections/artwork/category/10?filters=has_image%3Atrue+object_type_s%3APainting+collection_category%3A%220/European+Painting+and+Sculpture%22";


#http://www.artic.edu/aic/collections/artwork/department/21?page=1&filters=has_image%3Atrue+object_type_s%3APainting+collection_category%3A0/American
open (MYFILE, '>data_artic_paintings_european.txt');
print MYFILE "{\n";
print MYFILE "\"paintings\" : [\n";

$j = 1;
while ($j <=10) {
	$content = LWP::Simple::get($url);
	print $content;

	@page = split('details',$content);
	for ($i=1; $i<scalar(@page); $i++) {
		@link = split('href="',$page[$i]);
		@link = split('"',$link[1]);
		$link[0] = "http://www.artic.edu/" . $link[0];
		#print $link[0];print "\n";
	
		$content = LWP::Simple::get($link[0]);
		#print $content;
	
		@artist = split('tombstone">',$content);
		@artist = split('">',$artist[1]);	
		@artist = split('<br />',$artist[1]);
		@artist = $artist[0];
		if ($artist[0] eq "") {
			$nationality[0] = "";
			$birth = "";
			$death = "";
		}
		else {
			@nationality = split('tombstone">',$content);
			@nationality = split('">',$nationality[1]);
			@nationality = split('<br />',$nationality[1]);			
			@nationality = split('</a>',$nationality[1]);
			@nationality = split(',',$nationality[0]);
			#new expr added for year
			@year = split('tombstone">',$content);
			@year = split('">',$year[1]);
			@year = split('<br />',$year[1]);			
			@year = split('</a>',$year[1]);
			@year = split(',',$year[0]);
			$years = @year[scalar(@year)-1];
			
			$temp = substr($nationality[0],-1);
			if($temp eq ")") {
				$nationality[0] = 	substr($nationality[0],0,-1);
			}
			$nationality[0] = substr($nationality[0],1);
			
			$years = substr($years, 1);
			#print $years . "\n";
			$birth = substr($years, 0, 4);
			if ($birth =~ /^\d+$/)
			{
				$death = substr($years, -4);
				if ($death =~ /^\d+$/)
				{}
				else
				{
					$death="unknown";
					$birth="unknown";

				}
			}
			else
			{
				$birth = substr($years, -4);
				if ($birth =~ /^\d+$/)
				{}
				else
				{$birth = "unknown";}
				$death = "unknown";
			}
			
			#print $birth . " " . $death . "\n";
		}

		@image = split('artwork-image',$content);
		@image = split('src="',$image[1]);
		@image = split('"',$image[1]);
		$image[0] = $image[0];
		
		@title = split('tombstone"',$content);
		@title = split('italic-or-bold">',$title[1]);
		@title = split('</span>',$title[1]);
		$title[0] =~ s/"//g;
		@title = $title[0];
		
		@date = split('tombstone"',$content);
		@date = split('</span>',$date[1]);
		@date = split('</p>',$date[2]);
		$date = @date[0];
		#@date = split('class="twocol"',$content);
		#@date = split('<dt>Date</dt>',$date[1]);
		#@date = split('<dd>',$date[1]);
		#@date = split('</dd>',$date[1]);

		@medium = split('tombstone"',$content);
		@medium = split('</span>',$medium[1]);
		@medium = split('<p>',$medium[2]);
		@medium = split('<br />',$medium[1]);
		$medium[0] =~ s/\r\n//g;
		$medium[0] =~ s/^\s+|\s+$//g;
		@medium = $medium[0];

		#@medium = split('tombstone"',$content);
		#@medium = split('<dt>Medium</dt>',$medium[1]);
		#@medium = split('<dd>',$medium[1]);
		#@medium = split('</dd>',$medium[1]);
		#@medium = split('">',$medium[0]);
		#@medium = split('</a>',$medium[1]);
		#$medium[0] =~ s/\r\n//g;

		@dimensions = split('tombstone"',$content);
		@dimensions = split('</span>',$dimensions[1]);
		@dimensions = split('<p>',$dimensions[2]);
		@dimensions = split('<br />',$dimensions[1]);
		@dimensions = $dimensions[1] ;

		#@dimensions = split('class="twocol"',$content);
		#@dimensions = split('<dt>Dimensions</dt>',$dimensions[1]);
		#@dimensions = split('<dd>',$dimensions[1]);
		#@dimensions = split('</dd>',$dimensions[1]);
		$dimensions[0] =~ s/^\s+|\s+$//g;
		$dimensions[0] =~ s/\r\n//g;
		$dimensions[0] =~ s/\"/\\\"/g;

		@department = split('tombstone"',$content);

		@department = split('dept-gallery"',$department[1]);
		@department = split('">',$department[1]);
		@department = split('</a>',$department[1]);
		@department = $department[0];
		
		@classification = "Paintings";

		@credit = split('tombstone"',$content);
		@credit = split('</span>',$credit[1]);
		@credit = split('<br/>',$credit[2]);
		@credit = split('</p>',$credit[1]);

		@accession = split(',',$credit[0]);

		@credit = split(',',$credit[0]);

		$count = 0;
		$credit1 = "";
		while(scalar(@credit)-2 !=0 && $count <= scalar(@credit)-2)
		{
			$credit1 = $credit1.$credit[$count];
			if ($count != scalar(@credit)-2)
			{
				$credit1 = $credit1.", ";
			}
			$count++;
		}
		
		$accession[scalar(@accession)-1] =~ s/\t//g;
		$accession[scalar(@accession)-1] =~ s/\s//g;
		@accession = $accession[scalar(@accession)-1];

		if (scalar(@credit)-2 ==0)
		{
			$credit[0] =~ s/\t/ /g;
			@credit = $credit[0];		
		}
		else 
		{
			$credit1 =~ s/\t/ /g;
			@credit = $credit1;
		}

		$k = 30 * ($j-1) + $i;
		#print $k . "\n";
		
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
		#if ($k < 228 ) {
			print MYFILE ",";
		#}
	}
	
	$url = "http://www.artic.edu/aic/collections/artwork/category/10?page=". $j . "&filters=has_image%3Atrue+object_type_s%3APainting+collection_category%3A%220/European+Painting+and+Sculpture%22";

	$j++;
}

print MYFILE "]\n}\n";
close (MYFILE);