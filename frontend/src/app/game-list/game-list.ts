import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Api } from '../api';

@Component({
  selector: 'app-game-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './game-list.html',
  styleUrl: './game-list.css'
})
export class GameList implements OnInit {
  games = signal<any[]>([]);
  isAdmin = false;
  drawnGame = signal<any | null>(null); // Tu będziemy trzymać wylosowaną grę

  constructor(private api: Api) {}

  ngOnInit() {
    this.isAdmin = this.api.isAdmin();
    this.loadGames();
  }

  loadGames() {
    this.api.getGames().subscribe({
      next: (data) => this.games.set(data),
      error: (err) => console.error('Błąd przy pobieraniu gier:', err)
    });
  }

  delete(id: number) {
    this.api.deleteGame(id).subscribe({
      next: () => this.loadGames(),
      error: () => alert('Błąd usuwania!')
    });
  }

  drawRandomGame() {
    this.api.getRandomGame().subscribe({
      next: (game) => {
        this.drawnGame.set(game); // Ustawienie gry automatycznie pokaże wyskakujące okienko!
      },
      error: (err) => {
        console.error('Błąd losowania:', err);
        alert('Nie udało się wylosować gry. Sprawdź, czy masz gry w bazie!');
      }
    });
  }

  closeModal() {
    this.drawnGame.set(null); // Czyszczenie chowa okienko
  }

  logout() {
    this.api.logout();
  }
}
