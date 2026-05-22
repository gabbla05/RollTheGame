import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Api } from '../api';

@Component({
  selector: 'app-game-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './game-list.html'
})
export class GameList implements OnInit {
  // Zamieniamy zwykłą tablicę na Sygnał. To "krzyczy" do HTML-a, że dane się zmieniły!
  games = signal<any[]>([]);

  constructor(private api: Api) {}

  ngOnInit() {
    this.loadGames();
  }

  loadGames() {
    this.api.getGames().subscribe({
      next: (data) => {
        console.log('>>> POBRANE GRY Z BACKENDU:', data);
        this.games.set(data); // Wrzucamy dane z backendu prosto do sygnału
      },
      error: (err) => {
        console.error('Błąd przy pobieraniu gier:', err);
      }
    });
  }

  delete(id: number) {
    this.api.deleteGame(id).subscribe({
      next: () => this.loadGames(), // Odśwież po usunięciu
      error: () => alert('Brak uprawnień ADMINA do usuwania gier!')
    });
  }
}
