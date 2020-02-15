$(document).on('ready', function() {
	if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
		$(".regular").slick({
			dots: true,
    	    infinite: true,
    	    slidesToShow: 1,
    	    slidesToScroll: 1
    	    });
    	}
    	else
    	{
    	$(".regular").slick({
           dots: true,
           infinite: true,
           slidesToShow: 4,
           slidesToScroll: 4
	    });
    }
});