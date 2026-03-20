# Architecture Globale du Projet (Modular Monolith)
##  **Pourquoi pas des microservices ?**
 
je prends ce projet comme Une StartUp qui vient de lancer qui n'a pas de clients , pas une grose équipe de dev 
pas assez de moyens.
 
 Cette architecture est parfaitement découpé et découplé pour faciliter plus tard la migration en Microservices (Strangler Fig Pattern)
 Le piège que j'évite :

     ❌ Approche classique — MS dès le départ
     → Tu découpes selon ce que tu crois être indépendant
     → En prod tu découvres que Order et Payment
     se parlent 50 fois par seconde
     → Tu as une transaction distribuée ingérable
     → Tu passes 6 mois à implémenter des Sagas
     pour reproduire ce qu'une transaction SQL
     faisait en 10ms

    ✅ Mon Approche — Modular Monolith d'abord
    → Tu observes les vrais patterns en prod
    → Order et Payment fortement couplés
    → restent ensemble dans 1 seul MS
    → Notification totalement indépendant
    → extrait en MS proprement
    → Migration basée sur des données réelles
    pas sur des suppositions
C'est exactement ce que Sam Newman — l'auteur de "Building Microservices" — recommande dans son livre.
 Modules:
   - Module de Paiement (Payments)
   - Module des Produits (Products)
   - Module des Commandes (Order)

## Choix d'architecture (Clean Architecture)
 avec Compromis lier JPA et Spring Framework au Domain, c'est un TradeOff car nous avons une faible Probabilité de changer de Framework
 mais les Services tels que: Envoie d'email, SMS, et autre sont implémentés dans l'infrastructure car on suppose qu'elles ont une forte
 probabilité de changer dans le Temps.

## **Règle Stricte a respecter**
  - toutes les opérations de création et modifications renvoient l'ID de l'entité concerné. pour éviter les données unitiles coté réseau
  - Utiliser la ValueObject Money pour tout ce qui s'agit d'argent
  - Utiliser des projections ou des records pour récuperer Uniquement les informations néccessaires
  - 1 Use case = 1 DTO
  - Encapsulés les méthodes au sein des classes pas dans les services (Ce qui es le coeur meme de la POO) (exemple: )
  - 
                public void addItem(OrderItem item) {
                    if (item == null) {
                        return;
                    }
                    item.setOrder(this);
                    this.items.add(item);
                }
        
                 public Money calculateOrderTotalPrice() {
                    return items.stream().map(OrderItem::calculateItemPrice).reduce(Money.zero(), Money::add);
                }
                 public void changeSatus(OrderStatus orderStatus) {
                        if (!Objects.equals(orderStatus, this.orderStatus)) {
                        this.orderStatus = orderStatus;
                }
    }
  - 1 useCase = 1 Classe Java
  - Ecriture obligatoire de Tests unitaires et d'intégration
  - Publiez des evenements pour découpler les services et gagner en performance (utiliser la phase = AFTER_COMMIT au niveau des listeners).


## **Bonnes Pratique coté Optimisation**
 - Mettre par défaut toutes les entitiés en LAZY
 - Eviter le N+1 query problem en utilisant JOIN FETCH ou  des EntityGraph
 - éviter de faire des requettes dans des boucles, récuperer par batch avant de process
 - utilisez des Set par défaut sur toutes les éntités sauf des cas jugés utiles.