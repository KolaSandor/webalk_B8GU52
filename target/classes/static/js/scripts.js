document.addEventListener('DOMContentLoaded', () => {
    // Példa könyvlista betöltésére
    if (window.location.pathname === '/books.html') {
        fetch('/api/books')
            .then(response => response.json())
            .then(books => {
                const bookList = document.getElementById('bookList');
                books.forEach(book => {
                    const bookItem = document.createElement('div');
                    bookItem.innerHTML = `${book.title} - ${book.author}`;
                    bookList.appendChild(bookItem);
                });
            });
    }

    // Példa kölcsönzések listázására
    if (window.location.pathname === '/my-loans.html') {
        fetch('/api/loans')
            .then(response => response.json())
            .then(loans => {
                const loanList = document.getElementById('loanList');
                loans.forEach(loan => {
                    const loanItem = document.createElement('div');
                    loanItem.innerHTML = `${loan.bookTitle} - ${loan.returnDate}`;
                    loanList.appendChild(loanItem);
                });
            });
    }
});
