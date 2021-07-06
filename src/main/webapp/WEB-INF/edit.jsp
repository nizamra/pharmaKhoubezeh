
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en" xmlns:th="http://www.thymeleaf.org">



<head>
<title>Pharma &mdash; Khobeza</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link
	href="https://fonts.googleapis.com/css?family=Rubik:400,700|Crimson+Text:400,400i"
	rel="stylesheet">
<link rel="stylesheet" href="fonts/icomoon/style.css">

<link
	href="https://fonts.googleapis.com/css?family=Lato:300,400,700&display=swap"
	rel="stylesheet">

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/magnific-popup.css">
<link rel="stylesheet" href="css/jquery-ui.css">
<link rel="stylesheet" href="css/owl.carousel.min.css">
<link rel="stylesheet" href="css/owl.theme.default.min.css">


<link rel="stylesheet" href="css/aos.css">

<link rel="stylesheet" href="css/style.css">
<style>
.add {
	width: 50%;
	padding-top: 25px;
	margin: 0 auto;
	display: block;
	text-align: center;
}
</style>
</head>

<body>

	<div class="site-wrap">


		<div class="site-navbar py-2">

			<div class="search-wrap">
				<div class="container">
					<a href="#" class="search-close js-search-close"><span
						class="icon-close2"></span></a>
					<form action="#" method="post">
						<input type="text" class="form-control"
							placeholder="Search keyword and hit enter...">
					</form>
				</div>
			</div>

			<div class="container">
				<div class="d-flex align-items-center justify-content-between">
					<div class="logo">
						<div class="site-logo">
							<a href="index.html" class="js-logo-clone"><img
								style="width: 10vw; height: 5vw;" src="imgs/logo001.png"></a>
						</div>
					</div>
					<div class="main-nav d-none d-lg-block">
						<nav class="site-navigation text-right text-md-center"
							role="navigation">
						<ul class="site-menu js-clone-nav d-none d-lg-block">
							<li class="active"><a href="index.html">Home</a></li>


							<li><a href="about.html">About</a></li>
							<li><a href="#con">Contact</a></li>
						</ul>
						</nav>
					</div>
					<div class="icons">
						<a href="#" class="icons-btn d-inline-block js-search-open"><span
							class="icon-search"></span></a> <a href="cart.html"
							class="icons-btn d-inline-block bag"> <span
							class="icon-shopping-bag"></span> <span class="number">0</span>
						</a> <a href="#"
							class="site-menu-toggle js-menu-toggle ml-3 d-inline-block d-lg-none"><span
							class="icon-menu"></span></a>
					</div>
				</div>
			</div>
		</div>

		<div class="site-blocks-cover"
			style="background-image: url('imgs/pharm1.jpg');">
			<div class="container">
				<div class="row">
					<div class="col-lg-7 mx-auto order-lg-2 align-self-center">
						<div class="site-block-cover-content text-center">
							<h2 class="sub-title">Effective Medicine, New Medicine
								Everyday</h2>
							<h1 style="font-size: 30px;">
								Welcome
								<c:out value="${currentUser.username}"></c:out>
								To Pharma Khobeza
							</h1>
							<p>
								<a href="#c4" class="btn btn-primary px-5 py-3">Add product</a>
							</p>
							<p>
								<a href="#" class="btn btn-primary px-5 py-3">Show all
									Products </a>
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="title-section text-center col-12" >
							<h2 class="text-uppercase" style="padding-top:20px;">Edit Product</h2>
						</div>
		<form class="add" th:action="@{/products/save}" th:object="${product}"
			method="post" enctype="multipart/form-data">
			<div class="row justify-content-center">
				<div class="col-md-6 col-lg-4">
					<div class="login-wrap p-0">

						<div class="form-group">
							<label> Product Name:</label> <input class="form-control"
								type="text" name="name">
						</div>
						<br>

						<div class="form-group">
							<label>Description:</label> <input class="form-control"
								type="text" name="description">
						</div>
						<br>

						<div class="form-group">
							<label>Symptoms:</label> <input class="form-control" type="text"
								name="symptom">
						</div>
						<br>

						<div class="form-group">
							<label>Price:</label> <input class="form-control" type="number"
								name="price" />
						</div>
						<br>

						<div class="form-group">
							<label>Category:</label> <select class="form-select padd"
								name="category">
								<option th:each="cat: ${categories}" th:text="${ cat }" />
							</select>
						</div>
						<br>
						<div class="form-group">
							<label> photos:</label> <input type="file" name="image"
								accept="image/png, image/jpeg" />
						</div>
						<br>
					</div>
				</div>
			</div>


			<input type="submit" value="Add Product" class="btn btn-info" />
		</form>
		<br>
		<br>



		<div class="site-section bg-secondary bg-image"
			style="background-image: url('imgs/bg_2.jpg');">
			<div class="container">
				<div class="row align-items-stretch">
					<div class="col-lg-6 mb-5 mb-lg-0">
						<a href="#" class="banner-1 h-100 d-flex"
							style="background-image: url('imgs/bg_1.jpg');">
							<div class="banner-1-inner align-self-center">
								<h2>Pharma Khobeza Products</h2>
								<p>Medecine,Skin care products,child care products and
									beauty products.</p>
							</div>
						</a>
					</div>
					<div class="col-lg-6 mb-5 mb-lg-0">
						<a href="/all_products" class="banner-1 h-100 d-flex"
							style="background-image: url('imgs/bg_2.jpg');">
							<div class="banner-1-inner ml-auto  align-self-center">
								<h2>Pharmacy Trust</h2>
								<p>many of trusted pharmacies are connected with us .</p>
							</div>
						</a>
					</div>
				</div>
			</div>
		</div>


		<footer class="site-footer">
		<div class="container">
			<div class="row">
				<div class="col-md-6 col-lg-3 mb-4 mb-lg-0">

					<div class="block-7">
						<h3 class="footer-heading mb-4">About Us</h3>
						<p>Pharma khobeza is a page created to connect the people with
							the pharmacies around them and make it easier to buy products
							without the need to move around.</p>
					</div>

				</div>
				<div class="col-lg-3 mx-auto mb-5 mb-lg-0">
					<h3 class="footer-heading mb-4">Quick Links</h3>
					<ul class="list-unstyled">
						<li><a href="#">Supplements</a></li>
						<li><a href="#">Vitamins</a></li>

					</ul>
				</div>

				<div class="col-md-6 col-lg-3">
					<div class="block-5 mb-5">
						<h3 class="footer-heading mb-4">Contact Info</h3>
						<ul class="list-unstyled">
							<li class="address">18 Ersal St. Ramallah, West Bank,
								Palestine</li>
							<li class="phone"><a href="tel://2746534">+2 2746534</a></li>
							<li class="email">pharma.khoubezeh@gmail.com</li>
						</ul>
					</div>


				</div>
			</div>
			<div class="row pt-5 mt-5 text-center">
				<div class="col-md-12">
					<p>
						<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
						Copyright &copy;
						<script>
							document.write(new Date().getFullYear());
						</script>
						All rights reserved | made with <i class="icon-heart"
							aria-hidden="true"></i> by <a href="https://colorlib.com"
							target="_blank" class="text-primary">Pharma Khobeza</a>
						<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
					</p>
				</div>

			</div>
		</div>
		</footer>
	</div>

	<script src="js/jquery-3.3.1.min.js"></script>
	<script src="js/jquery-ui.js"></script>
	<script src="js/popper.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/owl.carousel.min.js"></script>
	<script src="js/jquery.magnific-popup.min.js"></script>
	<script src="js/aos.js"></script>

	<script src="js/main.js"></script>

</body>

</html>