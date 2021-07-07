<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Pharma &mdash; Colorlib Template</title>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<base href="/">
<link
	href="https://fonts.googleapis.com/css?family=Rubik:400,700|Crimson+Text:400,400i"
	rel="stylesheet">
<link rel="stylesheet" href="fonts/icomoon/style.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/magnific-popup.css">
<link rel="stylesheet" href="css/jquery-ui.css">
<link rel="stylesheet" href="css/owl.carousel.min.css">
<link rel="stylesheet" href="css/owl.theme.default.min.css">
<link rel="stylesheet" href="css/aos.css">
<link rel="stylesheet" href="css/style.css">
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
							<li class="active"><a href="/">Home</a></li>
							<li><a href="about.html">About</a></li>
							<li><a href="#con">Contact</a></li>
							<li><a href="/logout">logout</a></li>
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
		<div class="bg-light py-3">
			<div class="container">
				<div class="row">
					<div class="col-md-12 mb-0">
						<a href="index.html">Home</a> <span class="mx-2 mb-0">/</span> <strong
							class="text-black">Checkout</strong>
					</div>
				</div>
			</div>
		</div>

		<div class="site-section">
			<div class="container">
				<div class="row mb-5">
					<div class="col-md-12"></div>
				</div>
				<div class="row">
					<div class="col-md-6 mb-5 mb-md-0">
						<h2 class="h3 mb-3 text-black">Contact Information</h2>
						<div class="p-3 p-lg-5 border">
							<div class="form-group row"></div>
							<form:form action="/addingdata" method="POST" modelAttribute="user">
								<div class="form-group row">
									<div class="col-md-12">
										<label for="Address" class="text-black">Address <span
											class="text-danger">*</span></label>
										<form:errors path="address"/>
										<form:input type="text" class="form-control" id="Address"
											path="address" placeholder="Street address" />
									</div>
								</div>
								<div class="form-group row mb-5">

									<div class="col-md-6">
										<label for="Phone" class="text-black">Phone <span
											class="text-danger">*</span></label>
										<form:errors path="phone"/>
										<form:input type="text" class="form-control" id="Phone"
											path="phone" placeholder="Phone Number" />
									</div>
								</div>

								<div class="form-group">

									<div class="collapse" id="ship_different_address">
										<div class="py-2">
											<div class="form-group row">
												<div class="col-md-6">
													<label for="c_diff_fname" class="text-black">First
														Name <span class="text-danger">*</span>
													</label> <input type="text" class="form-control" id="c_diff_fname"
														name="c_diff_fname">
												</div>
												<div class="col-md-6">
													<label for="c_diff_lname" class="text-black">Last
														Name <span class="text-danger">*</span>
													</label> <input type="text" class="form-control" id="c_diff_lname"
														name="c_diff_lname">
												</div>
											</div>
											<div class="form-group row">
												<div class="col-md-12">
													<label for="c_diff_companyname" class="text-black">Company
														Name </label> <input type="text" class="form-control"
														id="c_diff_companyname" name="c_diff_companyname">
												</div>
											</div>

											<div class="form-group row">
												<div class="col-md-12">
													<label for="c_diff_address" class="text-black">Address
														<span class="text-danger">*</span>
													</label> <input type="text" class="form-control"
														id="c_diff_address" name="c_diff_address"
														placeholder="Street address">
												</div>
											</div>

											<div class="form-group">
												<input type="text" class="form-control"
													placeholder="Apartment, suite, unit etc. (optional)">
											</div>

											<div class="form-group row">
												<div class="col-md-6">
													<label for="c_diff_state_country" class="text-black">State
														/ Country <span class="text-danger">*</span>
													</label> <input type="text" class="form-control"
														id="c_diff_state_country" name="c_diff_state_country">
												</div>
												<div class="col-md-6">
													<label for="c_diff_postal_zip" class="text-black">Posta
														/ Zip <span class="text-danger">*</span>
													</label> <input type="text" class="form-control"
														id="c_diff_postal_zip" name="c_diff_postal_zip">
												</div>
											</div>

											<div class="form-group row mb-5">
												<div class="col-md-6">
													<label for="c_diff_email_address" class="text-black">Email
														Address <span class="text-danger">*</span>
													</label> <input type="text" class="form-control"
														id="c_diff_email_address" name="c_diff_email_address">
												</div>
												<div class="col-md-6">
													<label for="c_diff_phone" class="text-black">Phone
														<span class="text-danger">*</span>
													</label> <input type="text" class="form-control" id="c_diff_phone"
														name="c_diff_phone" placeholder="Phone Number">
												</div>
											</div>

										</div>

									</div>
								</div>

								<div class="form-group">
									<label for="c_order_notes" class="text-black">Order
										Notes</label>
									<textarea name="c_order_notes" id="c_order_notes" cols="30"
										rows="5" class="form-control"
										placeholder="Write your notes here..."></textarea>
								</div>
								<div class="form-group">
									<button type="submit" class="btn btn-primary btn-lg btn-block">Place
										Order</button>
								</div>
							</form:form>

						</div>
					</div>
					<div class="col-md-6">

						<div class="row mb-5"></div>

						<div class="row mb-5">
							<div class="col-md-12">
								<h2 class="h3 mb-3 text-black">Your Order</h2>
								<div class="p-3 p-lg-5 border">
									<table class="table site-block-order-table mb-5">
										<thead>
											<th>Product</th>
											<th>Total</th>
										</thead>
										<tbody>
											<c:forEach items="${ thisCart }" var="item">
												<tr>
													<td>${item.name}<strong class="mx-2">x</strong> 1
													</td>
													<td>${item.price}</td>
												</tr>
											</c:forEach>
											<tr>
												<td class="text-black font-weight-bold"><strong>Cart
														Subtotal</strong></td>
												<td class="text-black">$12.00</td>
											</tr>
											<tr>
												<td class="text-black font-weight-bold"><strong>Order
														Total</strong></td>
												<td class="text-black font-weight-bold"><strong>$12.00</strong></td>
											</tr>
										</tbody>
									</table>



								</div>
							</div>
						</div>

					</div>
				</div>
				<!-- </form> -->
			</div>
		</div>


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


		<footer class="site-footer" id="con">
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


</body>

</html>