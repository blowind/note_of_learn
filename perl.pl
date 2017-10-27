#!/usr/local/bin/perl -w



	use strict;
	use 5.014;
	
	my $infile = "";
	# my $outfile = "$infile"."";
	
	# my $flag = 0;
	
	open IN, '<', "$infile" or die "can't open $infile";
	# open OUT, '>', "$outfile" or die "can't open $outfile";
	
	# select OUT;
	
	
	while(<IN>) {
		chomp;
		my @ret = split;
	}
	
	# select STDOUT;
	close IN;
	# close OUT;