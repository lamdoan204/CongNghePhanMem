document.addEventListener("DOMContentLoaded", function () {
    // Lấy tất cả nút tăng/giảm
    document.querySelectorAll('.btn-plus, .btn-minus').forEach(button => {
        button.addEventListener('click', function () {
            updateQuantity(this, this.classList.contains('btn-plus') ? 1 : -1);
        });
    });

    // Hàm cập nhật số lượng sản phẩm và tổng giá
    function updateQuantity(button, delta) {
        const cartId = button.getAttribute('data-cart-id');
        const price = parseFloat(button.getAttribute('data-cart-price'));
        const input = button.parentElement.querySelector('.cart-quantity');
        const totalPriceElement = document.querySelector(`.total-price[data-cart-id="${cartId}"]`);

        // Tính số lượng mới
        let quantity = parseInt(input.value) + delta;
        if (quantity < 1) quantity = 1; // Không cho phép số lượng < 1
        input.value = quantity;

        // Cập nhật giá tổng cho sản phẩm đó
        const newTotalPrice = price * quantity;
        totalPriceElement.textContent = `$${newTotalPrice.toFixed(2)}`;

        // Tính toán tổng giá
        updateCartTotals();
    }

    // Hàm cập nhật tổng giá trị
    function updateCartTotals() {
        let subtotal = 0;

        // Duyệt qua tất cả các sản phẩm trong giỏ hàng
        document.querySelectorAll('.cart-quantity').forEach(input => {
            const quantity = parseInt(input.value);
            const price = parseFloat(input.getAttribute('data-cart-price'));
            subtotal += quantity * price;
        });

        // Cập nhật Subtotal và Total
        document.getElementById('subtotal-price').textContent = `$${subtotal.toFixed(2)}`;
        document.getElementById('total-price').textContent = `$${subtotal.toFixed(2)}`;
    }
});
