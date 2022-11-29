<%@page contentType="text/html" pageEncoding="UTF-8"%>


<div id="contact" class="bg-gray-800 text-slate-300 flex py-20 px-32">
    <div class="left basis-2/4">
        <div>
            <h2 class="text-2xl font-medium">Follow</h2>
            <p class="my-6">
                Exclusive offers, a heads up on new things, and sightings of Allbirds in the wild. Oh,
                we have cute sheep, too. #weareallbirds
            </p>
        </div>
        <div class="icon">
            <a href="https://www.facebook.com/annt202/" target="_blank">
                <i
                    class="fa-brands fa-facebook hover:scale-125 cursor-pointer transition-all duration-300"></i>
            </a>
            <a href="#">
                <i
                    class="fa-brands fa-instagram hover:scale-125 cursor-pointer transition-all duration-300"></i>
            </a>
            <a href="#">
                <i
                    class="fa-brands fa-twitter hover:scale-125 cursor-pointer transition-all duration-300"></i>
            </a>
            <a href="#">
                <i
                    class="fa-brands fa-github hover:scale-125 cursor-pointer transition-all duration-300"></i>
            </a>
            <a href="#">
                <i
                    class="fa-brands fa-youtube hover:scale-125 cursor-pointer transition-all duration-300"></i>
            </a>
        </div>
    </div>
    <div class="right basis-2/4 ml-12">
        <div>
            <h2 class="text-2xl font-medium">Have a question?</h2>
        </div>
        <div class="form-message">
            <form action="#">
                <input type="email" placeholder="Enter your email" name="email"
                       class="w-9/12 mt-6 mb-3 text-black outline-none p-1" /></br>
                <textarea id="txtArea" rows="5" class="w-full text-black mb-4 outline-none p-1"
                          placeholder="summary"></textarea>
                <input type="submit" value="SEND"
                       class="text-white bg-gray-500 py-1.5 px-4 hover:bg-white hover:text-black cursor-pointer">
            </form>
        </div>
    </div>
</div