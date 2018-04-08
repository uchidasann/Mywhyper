package edu.ncsu.csc.ase.dristi.shallowparser;

import java.util.Set;

import edu.ncsu.csc.ase.dristi.datastructure.Entity;
import edu.ncsu.csc.ase.dristi.datastructure.Relation;
import edu.ncsu.csc.ase.dristi.datastructure.Tuple;
import edu.ncsu.csc.ase.dristi.datastructure.type.RelationType;
import edu.ncsu.csc.ase.dristi.util.TupleUtil;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.trees.semgraph.SemanticGraph;

public class NSubjParser extends AbstractParser
{

	private static AbstractParser instance;
	
	public static synchronized AbstractParser getInstance() {
		if(instance == null)
		{
			instance = new NSubjParser();
		}
		return instance;
	}

	@Override
	public Tuple parse(IndexedWord gov, IndexedWord dep, SemanticGraph depGraph, Tuple t, Set<IndexedWord> visited) {
		
		Tuple t1;
		/*
		 * Check for LeafNode
		 */
		if(depGraph.getChildren(dep).size()>0)
		{
			t1 = parse(dep, depGraph, visited);
		}
		else
		{
			t1 = new Tuple(new Entity(dep));
		}
		
		
		Tuple t2 =  createTuple(gov);
		if(t2.isPartial())
		{
			t1 = new Tuple (new Relation(gov),t1);
		}
		else
		{
			t1 = new Tuple(new Tuple(new Entity(gov)), new Relation("is",RelationType.Symetric),t1);
		}
		
		t = (Tuple)TupleUtil.merge(t, t1 , gov.word());
		
		return t;
	}

}
