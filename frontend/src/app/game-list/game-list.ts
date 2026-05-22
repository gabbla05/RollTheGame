import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // <-- Wymagane dla formularza
import { Api } from '../api';

@Component({
  selector: 'app-game-list',
  standalone: true,
  imports: [CommonModule, FormsModule], // <-- Dodane tutaj!
  templateUrl: './game-list.html',
  styleUrl: './game-list.css'
})
export class GameList implements OnInit {
  games = signal<any[]>([]);
  users = signal<any[]>([]); // Sygnał dla admina
  isAdmin = false;
  drawnGame = signal<any | null>(null);

  // Zmienna trzymająca wpisywane dane nowej gry
  newGame = { title: '', genre: '', rating: 50 };

  constructor(private api: Api) {}

  ngOnInit() {
    this.isAdmin = this.api.isAdmin();

    // Jeśli Admin - ładujemy użytkowników. Jeśli User - ładujemy gry.
    if (this.isAdmin) {
      this.loadUsers();
    } else {
      this.loadGames();
    }
  }

  // --- FUNKCJE USERA ---
  loadGames() {
    this.api.getGames().subscribe({
      next: (data) => this.games.set(data),
      error: (err) => console.error(err)
    });
  }

  addGame() {
    this.api.addGame(this.newGame).subscribe({
      next: () => {
        this.loadGames(); // Odśwież listę
        this.newGame = { title: '', genre: '', rating: 50 }; // Wyczyść formularz
      },
      error: () => alert('Błąd! Sprawdź czy wypełniłeś wszystko poprawnie.')
    });
  }

  deleteGame(id: number) {
    this.api.deleteGame(id).subscribe({
      next: () => this.loadGames(),
      error: () => alert('Błąd usuwania!')
    });
  }

  drawRandomGame() {
    this.api.getRandomGame().subscribe({
      next: (game) => this.drawnGame.set(game),
      error: () => alert('Brak gier w bazie do wylosowania!')
    });
  }

  closeModal() {
    this.drawnGame.set(null);
  }

  // --- FUNKCJE ADMINA ---
  loadUsers() {
    this.api.getUsers().subscribe({
      next: (data) => this.users.set(data),
      error: (err) => console.error(err)
    });
  }

  deleteUser(id: number) {
    if (confirm('Na pewno chcesz usunąć tego użytkownika?')) {
      this.api.deleteUser(id).subscribe({
        next: () => this.loadUsers(),
        error: () => alert('Błąd usuwania użytkownika!')
      });
    }
  }

  logout() {
    this.api.logout();
  }
}
