[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.otaka/swinglayoutbuilder/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.otaka/swinglayoutbuilder)
# SwingLayoutBuilder
Java swing layout builder that helps to create complex forms.

Quick example:
```java
new SwingLayoutBuilder(frame).formLayout()
    .gapLabelToComponent(3)
    .gapBetweenRows(5)
    .gapBetweenColumns(10)
    .labelsRightAlignment()
    .add("First Name:", new JTextField("First name"))
    .add("Last Name:", new JTextField("Last name"))
    .add("Age:", new JTextField("Age"))
    .newColumn()
    .add("City:", new JTextField("City"))
    .add("Street:", new JTextField("Street"))
    .finish();
```

The builder supports several layouts to simplify some popular cases:
* Components placement with wrapping
* Simple form construction where we have several columns of label->component aligned by baseline
* Place component at left/right/top/left/center of parent component
* Constraint layout that is very similar to default SpringLayout, but much simpler. It just executes list of rules sequentially, that is why placement is very predictable.

###Maven
```xml
<dependency>
    <groupId>io.github.otaka</groupId>
    <artifactId>swinglayoutbuilder</artifactId>
    <version>0.2</version>
</dependency>
```



#SwingLayoutBuilder.borderLayout
It is just default java's BorderLayout wrapped in builder syntax
```java
new SwingLayoutBuilder(parentComponent).borderLayout()
    .gapBetweenComponents(5, 5)
    .addToTop(createColorBlock("Top", Color.lightGray))
    .addToRight(createColorBlock("Right", Color.CYAN))
    .addToBottom(createColorBlock("Bottom", Color.GREEN))
    .addToLeft(createColorBlock("Left", Color.YELLOW))
    .addToCenter(createColorBlock("Center", Color.WHITE))
    .preferredSize(300, 300)  // set size of last added component
    .finish();
```

#SwingLayoutBuilder.rowWithWrappingLayout
This is FlowLayout wrapped in builder syntax. It places the components in horizontal row.
All components touch each other(but you can specify gap between components), but whole group can be aligned left/right/center.
Also the components can be aligned vertically by baseline
```java
new SwingLayoutBuilder(frame).rowWithWrappingLayout()
    .gapBetweenComponents(5,5)
    .rowAlignmentRight()
    .baseLineAlignment(true)
    .componentPlacementDirection(ComponentOrientation.RIGHT_TO_LEFT)
    .add(createColorBlock("Comp1", Color.LIGHT_GRAY))
    .add(createColorBlock("Comp2", Color.CYAN))
    .add(createColorBlock("Comp3", Color.GREEN))
    .finish();
```

#FormLayoutBuilder.formLayout
This is simple form builder where we can have several columns of pairs label->component aligned by baseline
```java
new SwingLayoutBuilder(frame).formLayout()
                .gapLabelToComponent(3)   //interval between label and it's component
                .gapBetweenRows(5)        //vertical interval between rows
                .gapBetweenColumns(10)    //horizontal interval between two columns
                .labelsRightAlignment()   //should we align labels by right or left side
                .add("First Name:", new JTextField("First name"))
                .add("Last Name:", new JTextField("Last name"))
                .add("Age:", new JTextField("Age"))
                .newColumn()
                .add("City:", new JTextField("City"))
                .add("Street:", new JTextField("Street"))
                .finish();
```

#FormLayoutBuilder.constraintLayout
The  flexible layout that can bind any edge of component to other edges of other components.

It can work with components and reference them by id or reference them as **current** or **previous**.

**Current** component - component that was last added with method **add**, when you add next component,
the **current** becomes **previous** component and new component becomes **current** component.

Most important methods are link methods
* linkToPrevious - link current current component's edge to previous component's edge
* linkToParent - link current current component's edge to parent component's edge
* linkToId - link current component's edge to component that has specified id
* linkIdToId - link component with id to other component with id2
* linkToAnchor1..linkToAnchor3 - link current component's edge to component marked as anchorN



Example:
```java
new SwingLayoutBuilder(frame).constraintLayout()
    .add(createColorBlock("1 component", Color.WHITE)).preferredSize(100,100)
    .add(createColorBlock("2 component", Color.YELLOW)).preferredSize(100,100)
    .linkToPrevious(Edge.TOP, Edge.BOTTOM,7)         //place "2 component" below "1 component" with vertical gap 7 pixels
    .add(createColorBlock("3 component", Color.GREEN)).preferredSize(100,100)
    .linkToParent(Edge.HOR_CENTER,Edge.HOR_CENTER,0) //place "3 component" at center of parent
    .linkToParent(Edge.VER_CENTER,Edge.VER_CENTER,0)
    .add(new JLabel("4 component"))
    .linkToPrevious(Edge.BASELINE, Edge.BASELINE,0) //place "4 component" at right of "3 component" without gap
    .linkToPrevious(Edge.LEFT, Edge.RIGHT,5)        //and align it vertically by "3 component" baseline

    .add(createColorBlock("Bottom component", Color.CYAN))//attach component to bottom of parent
    .linkToParent(Edge.BOTTOM, Edge.BOTTOM, 1)             //and resize it with form resizing
    .linkToParent(Edge.LEFT, Edge.LEFT, 10)
    .linkToParent(Edge.RIGHT, Edge.RIGHT, -10)
        
    .add("square",createColorBlock("Square component", Color.MAGENTA))//add id to component, to allow reference it in next "link"
        .preferredSize(100,5)
    .linkToParent(Edge.RIGHT, Edge.RIGHT, 1)
    .linkToId(Edge.HEIGHT, "square", Edge.WIDTH, 0) //link component height to its own width, making itself square
    .finish();
```
###How the layout works
All link rules are collected in single list, and when Swing decides to layout the component, the layout just executes all link rules one by one. 
There is no any complex calculations to resolve constraints incompatibilities. 

You can easily create something like the following:
```java
    .linkToParent(Edge.LEFT, Edge.LEFT,1)
    .linkToParent(Edge.LEFT, Edge.LEFT,10)
```
In this case in placement time the current component at first will be moved to x=1, and then moved to x=10. And there are no any constraints violations.
###Anchors
If you have some layout where you have some anchor component and other components placed relative to it,
it will be not very convenient to use *linkToPrevious* because current and previous components will change everytime.
It will be possible to assign id to anchor component, and then link other components with *linkToId* method, but there is shortcut.
You can make something like this: 
```java
    .add(new JLabel("Some anchor"))
    .anchor1()
    ...
    .add(new JLabel("dependent child"))
    .linkToAnchor1(Edge.LEFT, Edge.LEFT, 0)
```
There are 3 anchors and there are methods to link to them:
* anchor1() linkToAnchor1()
* anchor2() linkToAnchor2()
* anchor3() linkToAnchor3()

You can freely mark any component as anchor after method **add(Component)**
```java
.add(new JLabel("Some anchor"))
    .anchor1()
.add(new JLabel("another anchor"))
    .anchor1() //mark new component as anchor1
.add(new JLabel("another anchor 2"))
    .anchor1()
```

###Groups
In case if you have group of components, and then you want attach the component to edge of bounding box of the group, you can use the following methods:

* **.createGroup(String id)** - create new component group with some id and make it current 
* **.addToCurrentGroup()** - add current component to current group
* **.addToGroup(String groupId)** - add current component to specified group

When the group is created, you can reference it's edges in link methods.

But there is limitations - you cannot move **a** group's edges, you can only link **to a** group's edges.

If you will try to move group's edge - it will not recalculate position of components inside the group. 