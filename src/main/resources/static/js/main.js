document.addEventListener("DOMContentLoaded", function () {
    // Xử lý sự kiện tăng số lượng
    document.querySelectorAll('.btn-plus').forEach(button => {
        button.addEventListener('click', function () {
            updateQuantity(this, 1); // Tăng số lượng
        });
    });

    // Xử lý sự kiện giảm số lượng
    document.querySelectorAll('.btn-minus').forEach(button => {
        button.addEventListener('click', function () {
            updateQuantity(this, -1); // Giảm số lượng
        });
    });

    // Hàm cập nhật số lượng sản phẩm
    function updateQuantity(button, delta) {
        const cartId = button.getAttribute('data-cart-id'); // ID sản phẩm trong giỏ
        const price = parseFloat(button.getAttribute('data-cart-price')); // Giá mỗi sản phẩm
        const input = button.closest('.quantity').querySelector('.cart-quantity');
        const totalPriceElement = document.querySelector(`.total-price[data-cart-id="${cartId}"]`);

        // Tính số lượng mới
        let quantity = parseInt(input.value) + delta;
        if (quantity < 1) quantity = 1; // Số lượng không nhỏ hơn 1
        input.value = quantity;

        // Cập nhật giá tổng cho từng sản phẩm
        const newTotalPrice = price * quantity;
        totalPriceElement.textContent = `$${newTotalPrice.toFixed(2)}`;

        // Gửi request cập nhật số lượng lên server
        updateCartOnServer(cartId, quantity);
    }

    // Gửi AJAX để cập nhật giỏ hàng
    function updateCartOnServer(cartId, quantity) {
        fetch(`/cart/update`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').content
            },
            body: JSON.stringify({ cartId, quantity })
        })
        .then(response => response.json())
        .then(data => {
            console.log(`Cart updated successfully: ${data.message}`);
            updateTotalCartPrice(data.totalCartPrice); // Cập nhật tổng giá của toàn bộ giỏ hàng
        })
        .catch(error => console.error('Error updating cart:', error));
    }

    // Cập nhật tổng giá của toàn bộ giỏ hàng
    function updateTotalCartPrice(newPrice) {
        const totalCartPriceElement = document.querySelector('.total-cart-price');
        if (totalCartPriceElement) {
            totalCartPriceElement.textContent = `$${newPrice.toFixed(2)}`;
        }
    }
});
