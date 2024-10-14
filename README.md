Random Walker is a program that generates different styles of "walkers" depending on user's input.
User can specify different Walker types, world geometries, and number of steps to take to create their own walker image.

Walker Behavior
  Standard Walker: walker picks a random surrounding spot and places a marker there until all steps are taken
  Picky Walker: walker takes 1-10 steps in any direction before changing, in which it will choose another direction and new length until all steps are taken. This creates a linear image

World Geometries
  Bounded world: A wall is created at the edge of the world that the walker cannot pass. If it reaches the edge it will choose a best possible move
  Toroidal world: If the walker reaches the edge of the world, it will be moved to the opposite edge of the world


https://github.com/user-attachments/assets/2cbf9e69-2aa6-4076-bd4b-5bd0bb90e751

